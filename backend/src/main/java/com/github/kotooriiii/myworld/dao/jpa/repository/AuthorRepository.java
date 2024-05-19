package com.github.kotooriiii.myworld.dao.jpa.repository;

import com.github.kotooriiii.myworld.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository
        extends JpaRepository<Author, UUID> {

    boolean existsAuthorByEmail(String email);
    boolean existsAuthorById(UUID id);
    Optional<Author> findAuthorByEmail(String email);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Author c SET c.imageIconId = :imageIconId WHERE c.id = :authorId") // can also do ?2 and ?1
    int uploadProfileImage(@Param("authorId") UUID authorId, @Param("imageIconId") String imageIconId);
}
