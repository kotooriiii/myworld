package com.github.kotooriiii.myworld.util.antlr.validation.factory;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JDBCResult;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;
import com.github.kotooriiii.myworld.util.antlr.validation.visitor.ExpressionJDBCVisitorImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JDBCDao<U extends GenericModel> extends DaoInterface<AttributeJDBCProcessingStrategy, JDBCResult, ExpressionJDBCVisitorImpl<U,?>>
{
    @Override
    public void processWithDefaultMappings(ExpressionJDBCVisitorImpl<U, ?> visitor, Set<String> visitedAttributes)
    {
        Map<String, JDBCResult> defaultAttributeQueryExpressionMap = this.getDefaultAttributeQueryExpressionMap();
        Set<String> defaultsRequiredToBeProcessedSet = new HashSet<>(defaultAttributeQueryExpressionMap.keySet());
        defaultsRequiredToBeProcessedSet.removeAll(visitedAttributes);


        for (String attributeName : defaultsRequiredToBeProcessedSet) {
            JDBCResult jdbcResult = defaultAttributeQueryExpressionMap.get(attributeName);
            visitor.getInnerJoinBuilder().add(jdbcResult.innerJoin());

            if(!visitor.isNoOp())
                visitor.getWhereClauseBuilder().append("AND ");

            visitor.getWhereClauseBuilder().append(jdbcResult.whereClause()).append(" ");
        }
    }
}
