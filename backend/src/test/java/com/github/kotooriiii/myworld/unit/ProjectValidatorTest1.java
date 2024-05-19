package com.github.kotooriiii.myworld.unit;

import com.github.kotooriiii.myworld.model.Project;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;

import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class ProjectValidatorTest1 {

    private static ProjectValidator projectValidator;
    @PersistenceContext
    EntityManager entityManager;
    @BeforeAll
    static void beforeAll()
    {
        projectValidator = new ProjectValidator();
    }

    @AfterAll
    static void afterAll()
    {
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testWithQueryExpression_jdbc_shouldReturnExpectedResult() {

        //Arrange
        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
        JDBCResult expectedJDBCResult = new JDBCResult("INNER JOIN " + "project_collaborators pc" + " ON " + "pc.author_id = id", "( title = REPLACE(REPLACE(REPLACE(:title, '\\', '\\\\'), '%', '\\%'), '_', '\\_') AND ( (TRUE) )  )", Map.of("title", "my cool project title :)"));

        // Act
        JDBCResult actualJDBCResult = projectValidator.withQueryExpression(filterQueryExpression).buildJDBC();

        //Assert
        Assertions.assertEquals(expectedJDBCResult, actualJDBCResult);

    }


    @Test
    void testWithQueryExpression_jpa_shouldReturnExpectedResult() {

        //Arrange

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> projectRoot = cq.from(Project.class);

        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
        Specification<Project> expectedJPASpecification = (root, query, criteriaBuilder) ->
        {
         return null;
        };

        JPAResult<Project> expectedJPAResult = new JPAResult<>(expectedJPASpecification);

        // Act
        JPAResult<Project> actualJPAResult = projectValidator.withQueryExpression(filterQueryExpression).buildJPA();

        //Assert
        Assertions.assertEquals(expectedJPAResult.specification().toPredicate(projectRoot, cq, cb), actualJPAResult.specification().toPredicate(projectRoot, cq, cb));

    }
}