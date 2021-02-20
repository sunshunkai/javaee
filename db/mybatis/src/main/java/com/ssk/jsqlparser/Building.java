package com.ssk.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * @author ssk
 * @date 2021/1/29
 */
public class Building {

    public static void main(String[] args) throws JSQLParserException {

        Insert insert = (Insert) CCJSqlParserUtil.parse("insert into mytable (col1) values (1)");
        System.out.println(insert.toString());

        //adding a column
        insert.getColumns().add(new Column("col2"));

        //adding a value using a visitor
        insert.getItemsList().accept(new ItemsListVisitor() {

            @Override
            public void visit(SubSelect subSelect) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void visit(ExpressionList expressionList) {
                expressionList.getExpressions().add(new LongValue(5));
            }

            @Override
            public void visit(NamedExpressionList namedExpressionList) {

            }

            @Override
            public void visit(MultiExpressionList multiExprList) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        System.out.println(insert.toString());

        //adding another column
        insert.getColumns().add(new Column("col3"));

        //adding another value (the easy way)
        ((ExpressionList)insert.getItemsList()).getExpressions().add(new LongValue(10));

        System.out.println(insert.toString());
    }
}
