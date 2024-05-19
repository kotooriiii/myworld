package com.github.kotooriiii.myworld.util.antlr.validation.result;

import java.util.Map;

public record JDBCResult(String innerJoin, String whereClause, Map<String, Object> valueMap) implements DaoResult
{
}
