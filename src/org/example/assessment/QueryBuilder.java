package org.example.assessment;

import java.util.ArrayList;
import java.util.List;


public class QueryBuilder {

    private List<Field> selectFields = new ArrayList<>();
    private Table fromTable;
    private List<Join> joins = new ArrayList<>();
    private List<Filter> filters = new ArrayList<>();
  
    public Query select(Field... fields) {
        for (Field field : fields) {
            selectFields.add(field);
        }
        return new Query(this);
    }
  
    public Query from(Table table) {
        this.fromTable = table;
        return new Query(this);
    }
  
    public Query join(Table table, Condition condition) {
        joins.add(new Join(table, condition));
        return new Query(this);
    }
  
    public Query filter(Condition condition) {
        filters.add(new Filter(condition));
        return new Query(this);
    }
  
    public Query build() {
        return new Query(this);
    }
  
    public List<Field> getSelectFields() {
        return selectFields;
    }
  
    public Table getFromTable() {
        return fromTable;
    }
  
    public List<Join> getJoins() {
        return joins;
    }
  
    public List<Filter> getFilters() {
        return filters;
    }
  
    public static Condition and(Condition condition1, Condition condition2) {
        return new SimpleCondition("(" + condition1.toString() + " AND " + condition2.toString() + ")");
    }
  
    public static Condition or(Condition condition1, Condition condition2) {
        return new SimpleCondition("(" + condition1.toString() + " OR " + condition2.toString() + ")");
    }
    
    public static Condition or(Condition condition1, Condition condition2, Condition condition3) {
    return new SimpleCondition("(" + condition1.toString() + " OR " + condition2.toString() + " OR " + condition3.toString() + ")");
    }

    public static Condition eq(Field field1, Field field2) {
        return new SimpleCondition(field1.getTable().getName() + "." + field1.getName() + " = " + field2.getTable().getName() + "." + field2.getName());
    }

    public static Condition eq(Field field, String value) {
      return new SimpleCondition(field.getTable().getName() + "." + field.getName() + " = '" + value + "'");
    }
  
    public static Condition eq(Field field, int value) {
      return new SimpleCondition(field.getTable().getName() + "." + field.getName() + " = " + value);
    }

    public static Condition gt(Field field, int value) {
        return new SimpleCondition(field.getTable().getName() + "." + field.getName() + " > " + value);
    }

    public static Condition like(Field field, String pattern) {
        return new SimpleCondition(field.getTable().getName() + "." + field.getName() + " LIKE '" + pattern + "'");
    }
  
    public static class Field {
        private final String name;
        private final Table table;
          
        public Field(Table table, String name) {
            this.name = name;
            this.table = table;
                    }
  
        public String getName() {
            return name;
        }
  
        public Table getTable() {
            return table;
        }
    }
  
    public static class Table {
        private final String name;
        private final String alias;
          
        public Table(String name) {
            this.name = name;
            this.alias = null;
                    }
  
        public Table(String name, String alias) {
            this.name = name;
            this.alias = alias;
                    }
  
        public String getName() {
            return name;
        }
  
        public String getAlias() {
            return alias;
        }
  
        public Field field(String fieldName) {
            return new Field(this, fieldName);
        }
    }
  
    public static class Join {
        private final Table table;
        private final Condition condition;
  
        public Join(Table table, Condition condition) {
            this.table = table;
            this.condition = condition;
        }
  
        public Table getTable() {
            return table;
        }
  
        public Condition getCondition() {
            return condition;
        }
    }
  
    public static class Filter {
        private final Condition condition;
  
        public Filter(Condition condition) {
            this.condition = condition;
        }
  
        public Condition getCondition() {
            return condition;
        }
    }
  }
