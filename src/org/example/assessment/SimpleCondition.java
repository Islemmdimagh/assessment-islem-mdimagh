package org.example.assessment;

public class SimpleCondition implements Condition {
    private final String condition;
  
    public SimpleCondition(String condition) {
        this.condition = condition;
    }
  
    @Override
    public String toString() {
        return condition;
    }
  }
