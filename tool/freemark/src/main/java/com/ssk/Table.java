package com.ssk;

import java.util.List;

/**
 * @author ssk
 * @date 2021/1/15
 */
public class Table {

    private String name;
    private String comment;
    private List<Column> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", columns=" + columns +
                '}';
    }

}
