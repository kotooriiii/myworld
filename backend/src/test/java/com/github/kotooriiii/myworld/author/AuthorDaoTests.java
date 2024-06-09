package com.github.kotooriiii.myworld.author;

import com.github.kotooriiii.myworld.AbstractDatabaseContainerBaseTest;
import com.github.kotooriiii.myworld.dao.AuthorDao;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.util.SystemEnvironmentUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.stream.Streams;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional()
public class AuthorDaoTests extends AbstractDatabaseContainerBaseTest
{

    @Autowired
    @Qualifier("jdbc")
    private AuthorDao jdbcAuthorDao;

    @Autowired
    @Qualifier("jpa")
    private AuthorDao jpaAuthorDao;

    @BeforeAll
    static void beforeAll()
    {
    }

    @AfterAll
    static void afterAll()
    {
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    Stream<AuthorDao> implementations()
    {
        return Streams.of(jdbcAuthorDao, jpaAuthorDao);
    }


    @ParameterizedTest
    @MethodSource("implementations")
    void AuthorDao_RegisterAuthor_ReturnRegisteredAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author author = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bob.hwei@gmail.com")
                .birthDate(LocalDate.of(2008, Month.APRIL, 12))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        //Act
        Author authorCreated = authorDao.createAuthor(author);

        //Assert
        Assertions.assertEquals(author, authorCreated);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAllAuthors_ReturnAllAuthors(AuthorDao authorDao)
    {
        //Arrange
        Author bob = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bob.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author jessica = Author.builder()
                .id(UUID.randomUUID())
                .name("Jessica Smolder")
                .email("jessica.smolder@hotmail.com")
                .birthDate(LocalDate.of(2012, Month.JULY, 3))
                .gender(Gender.FEMALE)
                .password("mycoolpassword")
                .imageIconId(null)
                .build();

        Author leah = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leah.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        List<Author> authorsArrangedList = Arrays.asList(bob, jessica, leah);

        for(Author author : authorsArrangedList)
            authorDao.createAuthor(author);

        //Act
        List<Author> selectAllAuthors = authorDao.selectAllAuthors();


        //Assert

        Assertions.assertEquals(authorsArrangedList.size(), selectAllAuthors.size());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(authorsArrangedList, selectAllAuthors));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAllAuthorsByIds_ReturnOnlyAuthorsWithSpecifiedIds(AuthorDao authorDao)
    {
        //Arrange
        Author bob = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bob.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author jessica = Author.builder()
                .id(UUID.randomUUID())
                .name("Jessica Smolder")
                .email("jessica.smolder@hotmail.com")
                .birthDate(LocalDate.of(2012, Month.JULY, 3))
                .gender(Gender.FEMALE)
                .password("mycoolpassword")
                .imageIconId(null)
                .build();

        Author leah = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leah.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        Author nick = Author.builder()
                .id(UUID.randomUUID())
                .name("Nick Norrie")
                .email("nick.norrie@ori.com")
                .birthDate(LocalDate.of(2006, Month.FEBRUARY, 1))
                .gender(Gender.MALE)
                .password("bells")
                .imageIconId(null)
                .build();

        List<Author> authorsArrangedList = Arrays.asList(bob, jessica, leah, nick);

        List<Author> authorsSelectAuthorsByIdExpected = Arrays.asList(bob,leah);

        for(Author author : authorsArrangedList)
            authorDao.createAuthor(author);

        //Act
        List<Author> selectAllAuthorsByIdsActual = authorDao.selectAllAuthorsByIds(authorsSelectAuthorsByIdExpected.stream().map(Author::getId).collect(Collectors.toList()));

        //Assert
        Assertions.assertEquals(authorsSelectAuthorsByIdExpected.size(), selectAllAuthorsByIdsActual.size());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(authorsSelectAuthorsByIdExpected, selectAllAuthorsByIdsActual));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAuthorsByPage_ReturnSizeAuthorsPerPage(AuthorDao authorDao)
    {
        //Arrange
        Author bob = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bob.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author jessica = Author.builder()
                .id(UUID.randomUUID())
                .name("Jessica Smolder")
                .email("jessica.smolder@hotmail.com")
                .birthDate(LocalDate.of(2012, Month.JULY, 3))
                .gender(Gender.FEMALE)
                .password("mycoolpassword")
                .imageIconId(null)
                .build();

        Author leah = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leah.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        Author nick = Author.builder()
                .id(UUID.randomUUID())
                .name("Nick Norrie")
                .email("nick.norrie@ori.com")
                .birthDate(LocalDate.of(2006, Month.FEBRUARY, 1))
                .gender(Gender.MALE)
                .password("bells")
                .imageIconId(null)
                .build();

        List<Author> authorsArrangedList = Arrays.asList(bob, jessica, leah, nick);

        List<Author> selectAuthorsByPage1Expected= Arrays.asList(bob,jessica);
        List<Author> selectAuthorsByPage2Expected = Arrays.asList(leah,nick);


        for(Author author : authorsArrangedList)
            authorDao.createAuthor(author);

        Pageable pageable = Pageable.ofSize(2).withPage(0);
        //Act
        List<Author> selectAuthorsByPage1Actual = authorDao.selectAuthorsByPage(pageable).getContent();
        List<Author> selectAuthorsByPage2Actual= authorDao.selectAuthorsByPage(pageable.next()).getContent();


        //Assert
        Assertions.assertEquals(selectAuthorsByPage1Expected.size(), selectAuthorsByPage1Actual.size());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(selectAuthorsByPage1Expected, selectAuthorsByPage1Actual));

        Assertions.assertEquals(selectAuthorsByPage2Expected.size(), selectAuthorsByPage2Actual.size());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(selectAuthorsByPage2Expected, selectAuthorsByPage2Actual));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAuthorById_ReturnCorrectAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        Optional<Author> leahActual = authorDao.selectAuthorById(leahExpected.getId());


        //Assert
        Assertions.assertTrue(leahActual.isPresent());
        Assertions.assertEquals(leahExpected, leahActual.get());
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAuthorById_ReturnDoesNotExistAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        Optional<Author> doesNotExistAuthor = authorDao.selectAuthorById(UUID.randomUUID());


        //Assert
        Assertions.assertFalse(doesNotExistAuthor.isPresent());
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAuthorByEmail_ReturnCorrectAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        Optional<Author> leahActual = authorDao.selectAuthorByEmail(leahExpected.getEmail());


        //Assert
        Assertions.assertTrue(leahActual.isPresent());
        Assertions.assertEquals(leahExpected, leahActual.get());
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_SelectAuthorByEmail_ReturnDoesNotExistAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        Optional<Author> doesNotExistAuthor = authorDao.selectAuthorByEmail("idontexist@gmail.com");


        //Assert
        Assertions.assertFalse(doesNotExistAuthor.isPresent());
    }

    //exists

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_ExistsAuthorById_ReturnAuthorExists(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        boolean leahExistsActual = authorDao.existsAuthorById(leahExpected.getId());


        //Assert
        Assertions.assertTrue(leahExistsActual);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_ExistsAuthorById_ReturnDoesNotExistAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        boolean doesNotExistAuthor = authorDao.existsAuthorById(UUID.randomUUID());


        //Assert
        Assertions.assertFalse(doesNotExistAuthor);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_ExistsAuthorByEmail_ReturnAuthorExists(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        boolean leahExistsActual = authorDao.existsAuthorByEmail(leahExpected.getEmail());


        //Assert
        Assertions.assertTrue(leahExistsActual);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_ExistsAuthorByEmail_ReturnDoesNotExistAuthor(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);


        //Act
        boolean doesNotExistAuthor = authorDao.existsAuthorByEmail("idontexist@gmail.com");


        //Assert
        Assertions.assertFalse(doesNotExistAuthor);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_UpdateAuthor_ReturnAuthorWithChanges(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();
        //Act

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);

        leahExpected.setName("Leah Marie");
        leahExpected.setBirthDate(LocalDate.of(1990, Month.SEPTEMBER, 13));

        authorDao.updateAuthor(leahExpected);

        Optional<Author> leahActual = authorDao.selectAuthorById(leahExpected.getId());


        //Assert
        Assertions.assertTrue(leahActual.isPresent());
        Assertions.assertEquals(leahExpected, leahActual.get());
        Assertions.assertEquals(leahExpected.getName(), leahActual.get().getName());
        Assertions.assertEquals(leahExpected.getBirthDate(), leahActual.get().getBirthDate());

    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_DeleteAuthorById_ReturnAuthorDoesNotExist(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();
        //Act

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);

        authorDao.deleteAuthorById(bobExpected.getId());

        boolean bobExistsActual = authorDao.existsAuthorById(bobExpected.getId());

        //Assert
        Assertions.assertFalse(bobExistsActual);
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void AuthorDao_DeleteAuthorById_ReturnOtherAuthorExists(AuthorDao authorDao)
    {
        //Arrange
        Author bobExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Bob Hwei")
                .email("bobExpected.hwei@gmail.com")
                .birthDate(LocalDate.of(1999, Month.DECEMBER, 15))
                .gender(Gender.MALE)
                .password("password123")
                .imageIconId(null)
                .build();

        Author leahExpected = Author.builder()
                .id(UUID.randomUUID())
                .name("Leah Nyoomnugget")
                .email("leahExpected.nyoomnugget@sweet.grl")
                .birthDate(LocalDate.of(2020, Month.FEBRUARY, 20))
                .gender(Gender.FEMALE)
                .password("flicka")
                .imageIconId(null)
                .build();
        //Act

        authorDao.createAuthor(bobExpected);
        authorDao.createAuthor(leahExpected);

        authorDao.deleteAuthorById(bobExpected.getId());

        boolean leahExistsActual = authorDao.existsAuthorById(leahExpected.getId());

        //Assert
        Assertions.assertTrue(leahExistsActual);
    }
}