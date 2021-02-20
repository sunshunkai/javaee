package com.ssk.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.util.StringUtils;

/**
 * @author ssk
 * @date 2021/2/4
 */
public class SqlParser {

    public static void parseSql(String sql) throws JSQLParserException {
        Statement stat = CCJSqlParserUtil.parse(sql);
        if (stat instanceof Select) {
//            parseSql((Select)stat);
        } else if (stat instanceof Update) {
//             parseSql((Update)stat);
        } else if (stat instanceof Delete) {
             parseSql((Delete)stat);
        } else if (stat instanceof Insert) {
//            parseSql((Insert)stat);
        } else {
            throw new JSQLParserException("不支持此类型的sql[" + sql + "]");
        }
    }



    private static void parseSql(Delete sql) throws JSQLParserException {
        Expression where = sql.getWhere();
        Table table = sql.getTable();

        String index = table.getSchemaName();
        String type = table.getName();
        if (StringUtils.hasText(index)) {
            System.out.println("TableName:"+index);
            System.out.println("TypeName:"+type);
        } else {
            System.out.println("TableName:"+index);
        }

//        sqlObj.setCondition(parseWhereExpress(table.getName(), where));
    }





}
