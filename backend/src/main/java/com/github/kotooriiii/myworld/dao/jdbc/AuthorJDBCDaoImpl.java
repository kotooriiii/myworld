package com.github.kotooriiii.myworld.dao.jdbc;

import com.github.kotooriiii.myworld.dao.AuthorDao;
import com.github.kotooriiii.myworld.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbc")
public class AuthorJDBCDaoImpl implements AuthorDao
{

    private final JdbcClient jdbcClient;

    @Override
    public Author createAuthor(Author authorToCreate)
    {
        var sql = """
                INSERT INTO authors(id, name, email, password, birth_date, gender)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        int result = jdbcClient.sql(sql)
                .params(Stream.of(authorToCreate.getId(), authorToCreate.getName(), authorToCreate.getEmail(), authorToCreate.getPassword(), authorToCreate.getBirthDate(), authorToCreate.getGender() == null ? null : authorToCreate.getGender().name()).toList())
                .update();
        Assert.state(result == 1, "Failed to create author with email : " + authorToCreate.getEmail());
        return authorToCreate;
    }


    @Override
    public List<Author> selectAllAuthors()
    {
        var sql = """
                SELECT *
                FROM authors
                LIMIT 1000
                """; //todo 1000 magic number?

        return jdbcClient.sql(sql).query(Author.class).list();
    }
    @Override
    public Page<Author> selectAuthorsByPage(Pageable pageable)
    {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        var sql = """
                SELECT *
                FROM authors
                LIMIT :limit
                OFFSET :offset
                """;

        List<Author> authors = jdbcClient.sql(sql)
                .param("limit", pageSize)
                .param("offset", offset)
                .query(Author.class)
                .list();

        var countSql = """
                SELECT COUNT(*)
                FROM authors
                """;
        int totalCount = jdbcClient.sql(countSql)
                .query(Integer.class)
                .single();

        return new PageImpl<Author>(authors, pageable, totalCount);
    }
    @Override
    public List<Author> selectAllAuthorsByIds(List<UUID> authorIds)
    {

        var sql = """
                SELECT *
                FROM authors
                WHERE id IN (:author_ids)
                """;
        return jdbcClient.sql(sql).param("author_ids", authorIds).query(Author.class).list();
    }

    @Override
    public Optional<Author> selectAuthorById(UUID id)
    {
        var sql = """
                SELECT *
                FROM authors
                WHERE id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Author.class)
                .optional();
    }

    @Override
    public Optional<Author> selectAuthorByEmail(String email)
    {
        var sql = """
                SELECT *
                FROM authors
                WHERE email = :email
                """;
        return jdbcClient.sql(sql)
                .param("email", email)
                .query(Author.class)
                .optional();
    }

    @Override
    public boolean existsAuthorByEmail(String email)
    {
        var sql = """
                SELECT count(id)
                FROM authors
                WHERE email = :email
                """;
        int count = jdbcClient.sql(sql)
                .param("email", email)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    @Override
    public boolean existsAuthorById(UUID id)
    {
        var sql = """
                SELECT count(id)
                FROM authors
                WHERE id = :id
                """;
        int count = jdbcClient.sql(sql)
                .param("id", id)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    @Override
    public void updateAuthor(Author authorToUpdate)
    {
        var sql = """
                UPDATE authors
                SET name = :name, email = :email, password = :password, birth_date = :birth_date, gender = :gender, image_icon_id = :image_icon_id
                WHERE id = :id
                """;
        var updated = jdbcClient.sql(sql)
                .param("name", authorToUpdate.getName())
                .param("email", authorToUpdate.getEmail())
                .param("password", authorToUpdate.getPassword())
                .param("birth_date", authorToUpdate.getBirthDate())
                .param("gender", authorToUpdate.getGender() == null ? null : authorToUpdate.getGender().name())
                .param("image_icon_id", authorToUpdate.getImageIconId())
                .param("id", authorToUpdate.getId())
                .update();

        Assert.state(updated == 1, "Failed to update author with email : " + authorToUpdate.getEmail());
    }

    @Override
    public void uploadProfileImage(UUID authorId, String image_icon_id)
    {
        //todo
        var sql =   """
                    UPDATE authors
                    SET image_icon_id = :image_icon_id
                    WHERE id = :id
                    """;
        var updated = jdbcClient.sql(sql)
                .param("image_icon_id", image_icon_id)
                .param("id", authorId)
                .update();

        Assert.state(updated == 1, "Failed to update author's image icon with ID  : " + authorId);
    }

    @Override
    public void deleteAuthorById(UUID id)
    {
        var sql = """
                DELETE
                FROM authors
                WHERE id = :id
                """;
        int result = jdbcClient.sql(sql)
                .param("id", id)
                .update();

        Assert.state(result == 1, "Failed to delete author with ID : " + id);
    }
}
