package org.example.assessment;

import org.example.assessment.QueryBuilder.Table;
import org.example.assessment.QueryBuilder.Field;

public class Query {

    private final QueryBuilder queryBuilder;
  
    public Query(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public Query select(Field... fields) {
        queryBuilder.select(fields);
        return this;
    }
  
    public Query from(Table table) {
        queryBuilder.from(table);
        return this;
    }
  
    public Query join(Table table, Condition condition) {
        queryBuilder.join(table, condition);
        return this;
    }
  
    public Query filter(Condition condition) {
        queryBuilder.filter(condition);
        return this;
    }
  
    public String build() {
        StringBuilder query = new StringBuilder("SELECT ");
        for (QueryBuilder.Field field : queryBuilder.getSelectFields()) {
            if (query.length() > "SELECT ".length()) {
                query.append(", ");
            }
            query.append(field.getTable().getName()).append(".").append(field.getName());
        }
        query.append(" FROM ").append(queryBuilder.getFromTable().getName());
        for (QueryBuilder.Join join : queryBuilder.getJoins()) {
            query.append(" JOIN ").append(join.getTable().getName());
            query.append(" AS ").append(join.getTable().getAlias());
            query.append(" ON ").append(join.getCondition().toString());
        }
        if (!queryBuilder.getFilters().isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < queryBuilder.getFilters().size(); i++) {
                if (i > 0) {
                    query.append(" AND ");
                }
                query.append(queryBuilder.getFilters().get(i).getCondition().toString());
            }
        }
        return query.toString();
    }
  }
  