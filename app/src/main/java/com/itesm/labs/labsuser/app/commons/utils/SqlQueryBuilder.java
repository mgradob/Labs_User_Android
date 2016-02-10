package com.itesm.labs.labsuser.app.commons.utils;

/**
 * Created by mgradob on 1/6/16.
 */
public class SqlQueryBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

    public SqlQueryBuilder() {
    }

    public SqlQueryBuilder select(String... parameters) {
        this.stringBuilder.append("SELECT ");

        for (int i = 0; i < parameters.length; i++) {
            String param = parameters[i];
            this.stringBuilder.append(param);

            if (i == parameters.length - 1) break;
            else this.stringBuilder.append(", ");
        }

        return this;
    }

    public SqlQueryBuilder from(String table) {
        this.stringBuilder.append(" FROM ");
        this.stringBuilder.append(table);

        return this;
    }

    public SqlQueryBuilder leftOuterJoin(String table, String column1, String column2) {
        this.stringBuilder.append(" LEFT OUTER JOIN ");
        this.stringBuilder.append(table);
        this.stringBuilder.append(" ON ");
        this.stringBuilder.append(column1);
        this.stringBuilder.append(" == ");
        this.stringBuilder.append(column2);

        return this;
    }

    public SqlQueryBuilder where(String condition1, String condition2, String operation) {
        this.stringBuilder.append(" WHERE ");
        this.stringBuilder.append(condition1);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(operation);
        this.stringBuilder.append(" ");
        this.stringBuilder.append(condition2);

        return this;
    }

    public String build() {
        return this.stringBuilder.toString();
    }
}