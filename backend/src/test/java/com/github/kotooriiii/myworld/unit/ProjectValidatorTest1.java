//package com.github.kotooriiii.myworld.unit;
//
//import com.github.kotooriiii.myworld.AbstractBaseTest;
//import com.github.kotooriiii.myworld.model.Project;
//import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
//import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;
//
//import com.github.kotooriiii.myworld.util.antlr.validation.arguments.MyProject;
//import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
//import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
//import com.github.kotooriiii.myworld.util.antlr.validation.validator.ProjectQEDefinition;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import org.junit.jupiter.api.*;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.Map;
//
//public class ProjectValidatorTest1 extends AbstractBaseTest
//{
//
//    @PersistenceContext
//    EntityManager entityManager;
//    @BeforeAll
//    static void beforeAll()
//    {
//    }
//
//    @AfterAll
//    static void afterAll()
//    {
//    }
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void testWithQueryExpression_jdbc_shouldReturnExpectedResult() {
//
//        //Arrange
//        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
//        JDBCResult expectedJDBCResult = new JDBCResult("INNER JOIN " + "project_collaborators pc" + " ON " + "pc.author_id = id", "( title = REPLACE(REPLACE(REPLACE(:title, '\\', '\\\\'), '%', '\\%'), '_', '\\_') AND ( (TRUE) )  )", Map.of("title", "my cool project title :)"));
//
//        // Act
//        JDBCResult actualJDBCResult = new MyProject().withAuthorRequesterId().withQueryExpression(filterQueryExpression).buildJDBC();
//
//        //Assert
//        Assertions.assertEquals(expectedJDBCResult, actualJDBCResult);
//
//    }
//
//
//    @Test
//    void testWithQueryExpression_jpa_shouldReturnExpectedResult() {
//
//        //Arrange
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
//        Root<Project> projectRoot = cq.from(Project.class);
//
//        QueryExpression filterQueryExpression = ExpressionUtils.getFilterQueryExpression("title eq \"my cool project title :)\"");
//        Specification<Project> expectedJPASpecification = (root, query, criteriaBuilder) ->
//        {
//         return null;
//        };
//
//        JPAResult<Project> expectedJPAResult = new JPAResult<>(expectedJPASpecification);
//
//        // Act
//        JPAResult<Project> actualJPAResult = projectQEDefinition.withQueryExpression(filterQueryExpression).buildJPA();
//
//        //Assert
//        Assertions.assertEquals(expectedJPAResult.specification().toPredicate(projectRoot, cq, cb), actualJPAResult.specification().toPredicate(projectRoot, cq, cb));
//
//    }
//}