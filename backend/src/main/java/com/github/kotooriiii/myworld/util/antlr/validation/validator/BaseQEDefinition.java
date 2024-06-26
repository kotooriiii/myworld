package com.github.kotooriiii.myworld.util.antlr.validation.validator;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.exception.FieldNotFoundException;
import com.github.kotooriiii.myworld.util.antlr.expression.QueryExpression;
import com.github.kotooriiii.myworld.util.antlr.util.ExpressionUtils;
import com.github.kotooriiii.myworld.util.antlr.validation.arguments.MyProject;
import com.github.kotooriiii.myworld.util.antlr.validation.factory.JDBCDao;
import com.github.kotooriiii.myworld.util.antlr.validation.factory.JPADao;
import com.github.kotooriiii.myworld.util.antlr.validation.factory.SpecFactory;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.AttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl.DefaultBooleanAttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl.DefaultNumberOrTimeAttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jdbc.impl.DefaultStringAttributeJDBCProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.AttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultBooleanAttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultNumberOrTimeAttributeJPAProcessingStrategy;
import com.github.kotooriiii.myworld.util.antlr.validation.strategy.jpa.impl.DefaultStringAttributeJPAProcessingStrategy;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public abstract class BaseQEDefinition<U extends GenericModel>
{
    private final Class<U> clazz;
    private final JPADao<U> jpaDao;
    private final JDBCDao<U> jdbcDao;
    private final Map<String, String> defaultAttributeMap;
    private final Set<String> requiredArguments;

    public BaseQEDefinition(Class<U> clazz)
    {
        this.clazz = clazz;

        jpaDao = new JPADao<>();
        jdbcDao = new JDBCDao<>();
        defaultAttributeMap = new HashMap<>();
        requiredArguments = new HashSet<>();

        addJPAAttributes(jpaDao.getAttributeStrategyMap());
        addJDBCAttributes(jdbcDao.getAttributeStrategyMap());
        addRequiredArguments(requiredArguments);
        addDefaultQueryExpression(defaultAttributeMap);
    }

    abstract void addJPAAttributes(Map<String, AttributeJPAProcessingStrategy> validAttributes);

    abstract void addJDBCAttributes(Map<String, AttributeJDBCProcessingStrategy> validAttributes);
    abstract void addRequiredArguments(Set<String> requiredArguments);



    abstract void addDefaultQueryExpression(Map<String, String> defaultAttributeMap);


    public Set<String> getRequiredArguments()
    {
        return requiredArguments;
    }

    public Map<String, String> getDefaultAttributeMap()
    {
        return defaultAttributeMap;
    }

    public JPADao<U> withJPA()
    {
        return jpaDao;
    }

    public JDBCDao<U> withJDBC()
    {
        return jdbcDao;
    }

    // Method to get default strategy for an attribute based on its data type in its class
    protected AttributeJPAProcessingStrategy getJPADefaultStrategyByFieldName(String attributeName)
    {
        // Determine the data type of the attribute based on its name,
        // throws runtime exception if no field with that name defined
        Class<?> fieldType = getFieldDataType(attributeName);

        // Determine the default strategy based on the field type
        if (UUID.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType))
        {
            return DefaultStringAttributeJPAProcessingStrategy.getInstance();
        } else if (Number.class.isAssignableFrom(fieldType) || LocalDate.class.isAssignableFrom(fieldType) || LocalDateTime.class.isAssignableFrom(fieldType))
        {
            return DefaultNumberOrTimeAttributeJPAProcessingStrategy.getInstance();
        } else if (Boolean.class.isAssignableFrom(fieldType))
        {
            return DefaultBooleanAttributeJPAProcessingStrategy.getInstance();
        }

        throw new UnsupportedOperationException("The data type in field \"" + attributeName + "\" in class \"" + clazz.getCanonicalName() + "\" is not supported.");
    }

    // Method to get default strategy for an attribute based on its data type in its class
    protected AttributeJDBCProcessingStrategy getJDBCDefaultStrategyByFieldName(String attributeName)
    {
        // Determine the data type of the attribute based on its name,
        // throws runtime exception if no field with that name defined
        Class<?> fieldType = getFieldDataType(attributeName);

        // Determine the default strategy based on the field type
        if (UUID.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType))
        {
            return DefaultStringAttributeJDBCProcessingStrategy.getInstance();
        } else if (Number.class.isAssignableFrom(fieldType) || LocalDate.class.isAssignableFrom(fieldType) || LocalDateTime.class.isAssignableFrom(fieldType))
        {
            return DefaultNumberOrTimeAttributeJDBCProcessingStrategy.getInstance();
        } else if (Boolean.class.isAssignableFrom(fieldType))
        {
            return DefaultBooleanAttributeJDBCProcessingStrategy.getInstance();
        }

        throw new UnsupportedOperationException("The data type in field \"" + attributeName + "\" in class \"" + clazz.getCanonicalName() + "\" is not supported.");
    }

    // Method to get the data type of attribute based on its name
    private Class<?> getFieldDataType(String attributeName)
    {
        try
        {
            // Use reflection to get the field from the entity class
            Field field = clazz.getDeclaredField(attributeName);
            // Return the type of the field
            return field.getType();
        } catch (NoSuchFieldException | SecurityException e)
        {
            // Handle exceptions
            e.printStackTrace();
            throw new FieldNotFoundException("There is no field defined with that name.", e);
        }

    }


}

