package com.github.kotooriiii.myworld.dao.jpa;

import com.github.kotooriiii.myworld.dao.AuthorDao;
import com.github.kotooriiii.myworld.dao.jpa.repository.AuthorRepository;
import com.github.kotooriiii.myworld.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Qualifier("jpa")
public class AuthorJPADaoImpl implements AuthorDao
{
    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author authorToCreate)
    {
        return authorRepository.save(authorToCreate);
    }

    @Override
    public List<Author> selectAllAuthors()
    {
        Page<Author> page = authorRepository.findAll(Pageable.ofSize(1000)); //todo magic number
        return page.getContent();
    }

    @Override
    public List<Author> selectAllAuthorsByIds(List<UUID> authorIds)
    {
        return authorRepository.findAllById(authorIds);
    }

    @Override
    public Page<Author> selectAuthorsByPage(Pageable pageable)
    {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<Author> selectAuthorById(UUID authorId)
    {
        return authorRepository.findById(authorId);
    }

    @Override
    public Optional<Author> selectAuthorByEmail(String email)
    {
        return authorRepository.findAuthorByEmail(email);
    }

    @Override
    public boolean existsAuthorByEmail(String email)
    {
        return authorRepository.existsAuthorByEmail(email);
    }

    @Override
    public boolean existsAuthorById(UUID authorId)
    {
        return authorRepository.existsAuthorById(authorId);
    }

    @Override
    public void updateAuthor(Author authorToUpdate)
    {
        authorRepository.save(authorToUpdate);
    }

    @Override
    public void uploadProfileImage(UUID authorId, String imageIconId)
    {
        authorRepository.uploadProfileImage(authorId, imageIconId);
    }

    @Override
    public void deleteAuthorById(UUID authorId)
    {
        authorRepository.deleteById(authorId);
    }
}
