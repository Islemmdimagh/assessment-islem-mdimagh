package org.example.assessment;


public class Query {

    private final QueryBuilder queryBuilder;
  
    public Query(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
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
  