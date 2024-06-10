package com.github.kotooriiii.myworld.unit;

import com.github.kotooriiii.myworld.AbstractDatabaseContainerBaseTest;
import com.github.kotooriiii.myworld.dao.AuthorDao;
import com.github.kotooriiii.myworld.dao.ProjectDao;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.model.ProjectCollaborator;
import com.github.kotooriiii.myworld.util.antlr.exception.BadRequestException;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.stream.Streams;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * Test class for ProjectValidator.
 */
@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional()
public class ProjectValidatorTest extends AbstractDatabaseContainerBaseTest
{

    /**
     * Record to hold AuthorDao and ProjectDao instances.
     */
    record DaoTestingContainer(AuthorDao authorDao, ProjectDao projectDao)
    {
    }


    @Autowired
    @Qualifier("jdbc")
    private AuthorDao jdbcAuthorDao;

    @Autowired
    @Qualifier("jpa")
    private AuthorDao jpaAuthorDao;

    @Autowired
    @Qualifier("jdbc")
    private ProjectDao jdbcProjectDao;

    @Autowired
    @Qualifier("jpa")
    private ProjectDao jpaProjectDao;

    private Map<String, Author> authorMap;
    private Map<String, Project> projectMap;

    @BeforeAll
    static void beforeAll()
    {
        // No-op
    }

    @AfterAll
    static void afterAll()
    {
        // No-op
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    /**
     * Provides a stream of DaoTestingContainer instances for parameterized tests.
     *
     * @return Stream of DaoTestingContainer instances
     */
    Stream<DaoTestingContainer> implementations()
    {
        return Streams.of(
                new DaoTestingContainer(jdbcAuthorDao, jdbcProjectDao),
                new DaoTestingContainer(jpaAuthorDao, jpaProjectDao)
        );
    }

    /**
     * Adds test authors to the database using the provided AuthorDao instance.
     *
     * @param authorDao AuthorDao instance to use
     */
    private void addAuthors(AuthorDao authorDao)
    {
        authorMap = new HashMap<>();

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
                .email("nick.norrie@ori.com") // TODO: Change names
                .birthDate(LocalDate.of(2006, Month.FEBRUARY, 1))
                .gender(Gender.MALE)
                .password("bells")
                .imageIconId(null)
                .build();

        authorMap.put(bob.getName(), bob);
        authorMap.put(jessica.getName(), jessica);
        authorMap.put(leah.getName(), leah);
        authorMap.put(nick.getName(), nick);

        authorMap.values().forEach(authorDao::createAuthor);
    }

    /**
     * Adds test projects to the database using the provided ProjectDao instance.
     *
     * @param projectDao ProjectDao instance to use
     */
    private void addProjects(ProjectDao projectDao)
    {
        projectMap = new HashMap<>();

        Project pA = new Project(UUID.randomUUID(), "my cool project title :)", "my cool description", null, LocalDateTime.now(), LocalDateTime.now(), authorMap.get("Bob Hwei").getId(), authorMap.get("Bob Hwei").getId(), null);
        Project pB = new Project(UUID.randomUUID(), "project B", "default description", null, LocalDateTime.now(), LocalDateTime.now(), authorMap.get("Leah Nyoomnugget").getId(), authorMap.get("Leah Nyoomnugget").getId(), null);
        Project pC = new Project(UUID.randomUUID(), "drawing proj %C", "drawing!!!", null, LocalDateTime.now(), LocalDateTime.now(), authorMap.get("Leah Nyoomnugget").getId(), authorMap.get("Leah Nyoomnugget").getId(), null);

        projectMap.put(pA.getTitle(), pA);
        projectMap.put(pB.getTitle(), pB);
        projectMap.put(pC.getTitle(), pC);

        projectMap.values().forEach(project ->
        {
            projectDao.createProject(project);
            projectDao.addCollaboratorByProjectIdAndByAuthorId(project.getId(), project.getCreatedBy(), ProjectCollaborator.AccessLevel.OWNER);
        });

        // Add collaborators to pA
        projectDao.addCollaboratorByProjectIdAndByAuthorId(pA.getId(), authorMap.get("Jessica Smolder").getId(), ProjectCollaborator.AccessLevel.EDITOR);

        // Add collaborator to pB
        projectDao.addCollaboratorByProjectIdAndByAuthorId(pB.getId(), authorMap.get("Bob Hwei").getId(), ProjectCollaborator.AccessLevel.EDITOR);
        projectDao.addCollaboratorByProjectIdAndByAuthorId(pB.getId(), authorMap.get("Jessica Smolder").getId(), ProjectCollaborator.AccessLevel.EDITOR);


        //Goals
        //Bob is owner of pA. Contributes to pB. Total: pA, pB
        //Jessica owns no projects. Contributes to pA and pB. Total: pA, pB
        //Leah is owner of pB and pC.  Contributes to no projects. Total: pB, pC
        //Nick owns no projects. Contributes to no projects. Total: n/a
    }

    /**
     * Tests the selectProjectsByPage method with a query expression using JDBC.
     *
     * @param daoTestingContainer DaoTestingContainer instance to use
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPageWithQueryExpression_shouldReturnMatchingProjects_whenTitleMatchesFilter(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        List<Project> expectedProjects = Collections.singletonList(projectMap.get("my cool project title :)"));

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Bob Hwei").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedProjects, actualProjects));
    }


    /**
     * Tests that the selectProjectsByPage method returns no projects when the title filter does not match any projects.
     *
     * @param daoTestingContainer the DaoTestingContainer instance to use for testing
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPageWithTitleFilter_shouldReturnNoMatchingProjects_whenTitleDoesNotMatchFilter(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"non-existing title\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Bob Hwei").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(actualProjects.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPageWithDescriptionFilter_shouldReturnMatchingProjects_whenDescriptionMatchesFilter(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("description eq \"default description\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        List<Project> expectedProjects = Collections.singletonList(projectMap.get("project B"));

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Leah Nyoomnugget").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedProjects, actualProjects));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPageWithDescriptionFilter_shouldReturnNoMatchingProjects_whenDescriptionMatchesFilterButIsNotTheCorrectUser(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("description eq \"default description\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        List<Project> expectedProjects = Collections.singletonList(projectMap.get("project B"));

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Bob Hwei").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedProjects, actualProjects));
    }


    /**
     * Tests that the selectProjectsByPage method returns projects with a collaborator type of "ALL" when the collaborator type filter is applied.
     *
     * @param daoTestingContainer the DaoTestingContainer instance to use for testing
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPageWithCollaboratorTypeFilter_shouldReturnMatchingProjects_whenCollaboratorTypeMatchesAllFilter(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("collaboratorType eq \"ALL\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        List<Project> expectedProjects = Arrays.asList(projectMap.get("my cool project title :)"), projectMap.get("project B"));

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Bob Hwei").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedProjects, actualProjects));
    }


    /**
     * Tests that the selectProjectsByPage method returns an empty list when there are no projects in the database for a user.
     *
     * @param daoTestingContainer the DaoTestingContainer instance to use for testing
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPage_shouldReturnEmptyList_whenThereAreNoProjects(DaoTestingContainer daoTestingContainer)
    {
        // Arrange

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
        Pageable pageable = Pageable.ofSize(1000).withPage(0);

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Nick Norrie").getId());
        List<Project> actualProjects = projects.getContent();

        // Assert
        Assertions.assertTrue(actualProjects.isEmpty());
    }


    /**
     * Tests that the selectProjectsByPage method throws an exception when the filter query is invalid.
     *
     * @param daoTestingContainer the DaoTestingContainer instance to use for testing
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPage_shouldThrowException_whenFilterQueryIsInvalid(DaoTestingContainer daoTestingContainer)
    {
        // Act and Assert
        Assertions.assertThrows(BadRequestException.class, () -> ExpressionUtils.getFilterQueryExpression("invalid query"));
    }


    /**
     * Tests that the selectProjectsByPage method returns multiple pages of results when there are multiple pages of projects.
     *
     * @param daoTestingContainer the DaoTestingContainer instance to use for testing
     */
    @ParameterizedTest
    @MethodSource("implementations")
    void testSelectProjectsByPage_shouldReturnMultiplePages_whenThereAreMultiplePagesOfResults(
            DaoTestingContainer daoTestingContainer)
    {
        // Arrange
        addAuthors(daoTestingContainer.authorDao);
        addProjects(daoTestingContainer.projectDao);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("");
        Pageable pageable = Pageable.ofSize(1).withPage(0);

        // Act
        Page<Project> projects = daoTestingContainer.projectDao.selectProjectsByPage(pageable, filterQueryExpression, authorMap.get("Bob Hwei").getId());

        // Assert
        Assertions.assertEquals(1, projects.getSize());
        Assertions.assertEquals(2, projects.getTotalElements());
        Assertions.assertEquals(2, projects.getTotalPages());
    }
}