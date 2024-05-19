package com.github.kotooriiii.myworld.dao;

import com.github.kotooriiii.myworld.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorDao
{
    Author createAuthor(Author authorToCreate);
    List<Author> selectAllAuthors();
    Page<Author> selectAuthorsByPage(Pageable pageable); //todo impl in controller
    List<Author> selectAllAuthorsByIds(List<UUID> authorIds);
    Optional<Author> selectAuthorById(UUID authorId);
    Optional<Author> selectAuthorByEmail(String email);
    boolean existsAuthorByEmail(String email);
    boolean existsAuthorById(UUID authorId);
    void updateAuthor(Author authorToUpdate);
    void uploadProfileImage(UUID authorId, String imageIconId);
    void deleteAuthorById(UUID authorId);
}
