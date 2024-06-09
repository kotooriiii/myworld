package com.github.kotooriiii.myworld.util.antlr.validation.factory;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.exception.FieldNotFoundException;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultBooleanAttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultNumberOrTimeAttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultStringAttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJPAVisitorImpl;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JPADao<U extends GenericModel> extends DaoInterface<AttributeJPAProcessingStrategy, JPAResult<U>, ExpressionJPAVisitorImpl<U, ?>>
{
    @Override
    public void processWithDefaultMappings(ExpressionJPAVisitorImpl<U, ?> visitor, Set<String> visitedAttributes)
    {
        Map<String, JPAResult<U>> defaultAttributeQueryExpressionMap = this.getDefaultAttributeQueryExpressionMap();
        Set<String> defaultsRequiredToBeProcessedSet = new HashSet<>(defaultAttributeQueryExpressionMap.keySet());
        defaultsRequiredToBeProcessedSet.removeAll(visitedAttributes);


        Specification<U> root = visitor.getSpecification();

        assert root != null; //should not occur since it's called after


        for (String attributeName : defaultsRequiredToBeProcessedSet) {
            JPAResult<U> jpaResult = defaultAttributeQueryExpressionMap.get(attributeName);
            root = root.and(jpaResult.specification());
        }

        visitor.setFinalSpecification(root);
    }
}
