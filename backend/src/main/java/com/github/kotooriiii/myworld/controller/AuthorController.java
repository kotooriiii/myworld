package com.github.kotooriiii.myworld.controller;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.request.author.AuthorUpdateRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.service.AuthorService;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController
{
    private final AuthorService authorService;
    private final JWTUtil jwtUtil;

    public AuthorController(AuthorService authorService,
                            JWTUtil jwtUtil)
    {
        this.authorService = authorService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> registerAuthor(
            @RequestBody AuthorRegistrationRequest request)
    {
        AuthorDTO authorDTO = authorService.createAuthor(request);

        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body(authorDTO);
    }


    @GetMapping("account")
    public AuthorDTO getAccountInfo()
    {
        UUID authorRequesterId = authorService.getCurrentUserId();
        return getAuthor(authorRequesterId);
    }

    @GetMapping("all")
    public List<AuthorDTO> getAuthors()
    {
        return authorService.getAllAuthors();
    }

    @GetMapping
    public Page<AuthorDTO> getAuthorsByPage(@PageableDefault(size = 20, page = 0) Pageable pageable)
    {
        return authorService.getAuthorsByPage(pageable);
    }

    @GetMapping("{authorIds}")
    public Map<UUID, AuthorDTO> getAuthorsByIds(
            @PathVariable("authorIds") List<UUID> authorIds)
    {
        return authorService.getAllAuthorsByIds(authorIds);
    }

    @GetMapping("{authorId}")
    public AuthorDTO getAuthor(
            @PathVariable("authorId") UUID authorId)
    {
        return authorService.getAuthor(authorId);
    }

    @PutMapping("{authorId}")
    public void updateAuthor(
            @PathVariable("authorId") UUID authorId,
            @RequestBody AuthorUpdateRequest updateRequest)
    {
        authorService.updateAuthor(authorId, updateRequest);
    }

    @PostMapping(value = "{authorId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAuthorProfileImage(@PathVariable("authorId") UUID authorId, @RequestParam("file")
    MultipartFile file)
    {
        authorService.uploadAuthorProfileImage(authorId, file);
    }

    @DeleteMapping("{authorId}")
    public void deleteAuthor(
            @PathVariable("authorId") UUID authorId)
    {
        authorService.deleteAuthorById(authorId);
    }


    @GetMapping(value = "{authorId}/profile-image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getAuthorProfileImage(@PathVariable("authorId") UUID authorId)
    {
        return authorService.getAuthorProfileImage(authorId);
    }
}
