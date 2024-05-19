package com.github.kotooriiii.myworld.service;


import com.github.kotooriiii.myworld.dao.AuthorDao;
import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.request.author.AuthorUpdateRequest;
import com.github.kotooriiii.myworld.exception.DuplicateResourceException;
import com.github.kotooriiii.myworld.exception.RequestValidationException;
import com.github.kotooriiii.myworld.exception.ResourceNotFoundException;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.service.mapper.AuthorMapper;
import com.github.kotooriiii.myworld.service.s3.S3Buckets;
import com.github.kotooriiii.myworld.service.s3.S3Service;
import com.github.kotooriiii.myworld.service.security.userDetailsService.AuthorUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorService
{

    private final AuthorDao authorDao;
    private final AuthorMapper authorMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    public AuthorService(@Qualifier("jdbc") AuthorDao authorDao,
                         AuthorMapper authorMapper,
                         PasswordEncoder passwordEncoder,
                         S3Service s3Service,
                         S3Buckets s3Buckets) {
        this.authorDao = authorDao;
        this.authorMapper = authorMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorDao.selectAllAuthors()
                .stream()
                .map(authorMapper)
                .collect(Collectors.toList());
    }

    public Page<AuthorDTO> getAuthorsByPage(Pageable pageable)
    {
        Page<Author> page = authorDao.selectAuthorsByPage(pageable);
        return page.map(authorMapper);
    }

    public Map<UUID, AuthorDTO> getAllAuthorsByIds(List<UUID> authorIds) {
        return authorDao.selectAllAuthorsByIds(authorIds)
                .stream()
                .map(authorMapper)
                .collect(Collectors.toMap(AuthorDTO::id, Function.identity()));
    }

    public AuthorDTO getAuthor(UUID id) {
        return authorDao.selectAuthorById(id)
                .map(authorMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "author with id [%s] not found".formatted(id)
                ));
    }

    public AuthorDTO createAuthor(AuthorRegistrationRequest authorRegistrationRequest) {
        // check if email exists
        String email = authorRegistrationRequest.email();
        if (authorDao.existsAuthorByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // add
        UUID authorId = UUID.randomUUID();

        Author author = Author.builder()
                .id(authorId)
                .name(authorRegistrationRequest.name())
                .email(authorRegistrationRequest.email())
                .password(passwordEncoder.encode(authorRegistrationRequest.password()))
                .birthDate(authorRegistrationRequest.birthDate())
                .gender(authorRegistrationRequest.gender())
                .imageIconId(null)
                .build();

       return authorMapper.apply(authorDao.createAuthor(author));
    }

    public void deleteAuthorById(UUID authorId) {
        checkIfAuthorExistsOrThrow(authorId);
        authorDao.deleteAuthorById(authorId);
    }

    private void checkIfAuthorExistsOrThrow(UUID authorId)
    {
        if (!authorDao.existsAuthorById(authorId)) {
            throw new ResourceNotFoundException(
                    "author with id [%s] not found".formatted(authorId)
            );
        }
    }

    public void updateAuthor(UUID authorId,
                             AuthorUpdateRequest updateRequest) {
        // TODO: for JPA use .getReferenceById(authorId) as it does does not bring object into memory and instead a reference
        Author author = authorDao.selectAuthorById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "author with id [%s] not found".formatted(authorId)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(author.getName())) {
            author.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.birthDate() != null && !updateRequest.birthDate().equals(author.getBirthDate())) {
            author.setBirthDate(updateRequest.birthDate());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(author.getEmail())) {
            if (authorDao.existsAuthorByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            author.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
           throw new RequestValidationException("no data changes found");
        }

        authorDao.updateAuthor(author);
    }

    public void uploadAuthorProfileImage(UUID authorId,
                                         MultipartFile file) {
        checkIfAuthorExistsOrThrow(authorId);
        String imageIconId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getAuthor(),
                    "profile-images/%s/%s".formatted(authorId, imageIconId), //todo how to not hard dcode this path
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }
        authorDao.uploadProfileImage(authorId, imageIconId);
    }

    public byte[] getAuthorProfileImage(UUID authorId) {
        var author = authorDao.selectAuthorById(authorId)
                .map(authorMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "author with id [%s] not found".formatted(authorId)
                ));


        if (StringUtils.isBlank(author.imageIconId())) {
            throw new ResourceNotFoundException(
                    "author with id [%s] profile image not found".formatted(authorId));
        }

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getAuthor(),
                "profile-images/%s/%s".formatted(authorId, author.imageIconId())
        );
        return profileImage;
    }


    // User authentication

    public Author getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Author) {
            return (Author) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    public UUID getCurrentUserId() {
        Author author = getCurrentUserDetails();
        if(author != null)
            return author.getId();
        return null;
    }
}

