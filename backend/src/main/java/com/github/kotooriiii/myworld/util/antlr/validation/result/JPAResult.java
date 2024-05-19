package com.github.kotooriiii.myworld.util.antlr.validation.result;

import com.github.kotooriiii.myworld.model.GenericModel;
import org.springframework.data.jpa.domain.Specification;

public record JPAResult<U extends GenericModel>(Specification<U> specification) implements DaoResult
{
}
