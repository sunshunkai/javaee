package com.ssk;

/**
 * @author ssk
 * @date 2021/1/15
 */
public class Column {

    private Integer index;
    private String name;
    private String dataType;
    private String  constraint;
    private String comment;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Column{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", constraint='" + constraint + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
