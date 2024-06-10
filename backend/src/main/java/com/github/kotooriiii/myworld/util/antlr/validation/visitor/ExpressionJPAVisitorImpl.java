package com.github.kotooriiii.myworld.util.antlr.validation.visitor;

import com.github.kotooriiii.myworld.model.GenericModel;
import com.github.kotooriiii.myworld.util.antlr.expression.*;
import com.github.kotooriiii.myworld.util.antlr.validation.factory.SpecFactory;
import com.github.kotooriiii.myworld.util.antlr.validation.result.JPAResult;
import com.github.kotooriiii.myworld.util.antlr.validation.validator.BaseQEDefinition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Stack;

@Getter
public class ExpressionJPAVisitorImpl<U extends GenericModel, D extends BaseQEDefinition<U>> extends ExpressionVisitor<U, D, JPAResult<U>>
{

    private Root<U> root;
    private CriteriaQuery<?> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    private Specification<U> specification; //builder
    private Specification<U> finalSpecification;

    private final Stack<Predicate> predicateStack;

    public ExpressionJPAVisitorImpl(SpecFactory<U,D> udSpecFactory)
    {
        super(udSpecFactory);
        predicateStack = new Stack<>();
    }

    @Override
    public <T extends Comparable<? super T>> void visit(ConditionalExpression<T> expression)
    {
        this.specFactory.getBaseQEDefinition().withJPA().checkExpressionValidOrThrow(expression);
        this.specFactory.getBaseQEDefinition().withJPA().getProcessingStrategy(expression.getAttribute()).process(this, expression);
        visitedAttributes.add(expression.getAttribute());

    }

    @Override
    public void visit(ConditionalOperator conditionalOperator)
    {
        //defined with visit(ConditionalExpression)
        //do not implement here!
    }


    @Override
    public void visit(LogicalExpression expression)
    {

        expression.getLeftHand().accept(this);
        expression.getRightHand().accept(this);

        Predicate right = predicateStack.pop();
        Predicate left = predicateStack.pop();

        Predicate predicate = switch (expression.getOperator())
        {
            case AND -> criteriaBuilder.and(left, right);
            case OR -> criteriaBuilder.or(left, right);
        };
        predicateStack.push(predicate);

    }

    @Override
    public void visit(LogicalOperator logicalOperator)
    {
        //defined with visit(LogicalExpression)
        //do not implement here!
    }

    @Override
    public void visit(GroupExpression expression)
    {
        expression.getExpression().accept(this);
    }
    @Override
    public JPAResult<U> build(boolean isDefaultAttribute)
    {
        this.specification =
                (root, query, criteriaBuilder) ->
                {
                    ExpressionJPAVisitorImpl.this.root = root;
                    ExpressionJPAVisitorImpl.this.criteriaQuery = query;
                    ExpressionJPAVisitorImpl.this.criteriaBuilder = criteriaBuilder;
                    ExpressionJPAVisitorImpl.this.specFactory.getQueryExpression().accept(ExpressionJPAVisitorImpl.this);

                    if(isNoOp())
                        predicateStack.push(criteriaBuilder.and()); //always true statement! in case, no-op

                    return predicateStack.pop();
                };



        if(!isDefaultAttribute)
        {
            this.specFactory.getBaseQEDefinition().withJPA().processWithDefaultMappings(this, visitedAttributes); //sets final spec here
        }
        else
        {
            setFinalSpecification(getSpecification());
        }


        return new JPAResult<>(getFinalSpecification());
    }

    public void setFinalSpecification(Specification<U> finalSpecification)
    {
        this.finalSpecification = finalSpecification;
    }
}
