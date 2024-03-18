package org.example.assessment;

import static org.example.assessment.QueryBuilder.*;

public class Main {

  public static void main(String[] args) {
    QueryBuilder queryBuilder = new QueryBuilder();

    Table users = new Table("users");
    Table comments = new Table("comments", "comment_alias");

    Query query = (
        queryBuilder
            .select(
                users.field("id"),
                users.field("name"),
                comments.field("text")
            )
            .from(users)
            .join(comments, and(eq(users.field("id"), comments.field("user_id")),
                                eq(comments.field("status"), "active")))
            .filter(gt(users.field("id"), 100))
            .filter(like(users.field("name"), "test%"))
            .filter(or(
                eq(users.field("id"), 200),
                eq(comments.field("text"), "no-text"),
                and(
                    eq(users.field("id"), 300),
                    eq(users.field("name"), "Hans")
                )
            )));

    String stmt = query.build();

    System.out.println(stmt);
  }

}

