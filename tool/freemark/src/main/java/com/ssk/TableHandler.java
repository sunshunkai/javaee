package com.ssk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ssk
 * @date 2021/1/15
 */
public class TableHandler {
    /**
     * @param args
     */
    //驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    //连接地址是由各个数据库生产商单独提供的，所以需要单独记住
    public static final String DBURL = "jdbc:mysql://localhost:3306/cnooc_item";
    //连接数据库的用户名
    public static final String DBUSER = "root";
    //连接数据库的密码
    public static final String DBPASS = "ssk123456";


    public static List<String> getTableNames() throws Exception
    {
        List<String> tableNames=new ArrayList<String>();
        Connection con = null; //表示数据库的连接对象
        Statement stmt = null;  //表示数据库的更新操作
        ResultSet result = null; //表示接收数据库的查询结果
        Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
        con = DriverManager.getConnection(DBURL,DBUSER,DBPASS); //2、连接数据库
        stmt = con.createStatement(); //3、Statement 接口需要通过Connection 接口进行实例化操作
        result = stmt.executeQuery("show tables"); //执行SQL 语句，查询数据库
        while (result.next()){
            String name = result.getString(1);
            tableNames.add(name);
        }
        result.close();
        con.close(); // 4、关闭数据库
        return tableNames;
    }



    public static List<Table> getTables() throws Exception
    {
        List<Table> tables=new ArrayList<Table>();
        Connection con = null; //表示数据库的连接对象
        Statement stmt = null;  //表示数据库的更新操作
        ResultSet result = null; //表示接收数据库的查询结果
        Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
        con = DriverManager.getConnection(DBURL,DBUSER,DBPASS); //2、连接数据库
        stmt = con.createStatement(); //3、Statement 接口需要通过Connection 接口进行实例化操作
        result = stmt.executeQuery("select table_name,table_comment from information_schema.tables where table_schema = 'cnooc_item' \n"); //执行SQL 语句，查询数据库
        while (result.next()){
            String name = result.getString(1);
            String comment = result.getString(2);
            Table table=new Table();
            table.setName(name);
            table.setComment(comment);
            tables.add(table);
        }
        for(Table table:tables)
        {
            List<Column>columns=new ArrayList<Column>();
            //查询字段
            result = stmt.executeQuery("select ORDINAL_POSITION,COLUMN_NAME,COLUMN_TYPE,EXTRA,COLUMN_COMMENT from information_schema.columns where table_schema ='cnooc_item' and table_name = '"+table.getName()+"' "); //执行SQL 语句，查询数据库
            while (result.next()){
                int index = result.getInt(1);
                String name = result.getString(2);
                String type = result.getString(3);
                String constraint = result.getString(4);
                String comment = result.getString(5);
                Column column=new Column();
                column.setIndex(index);
                column.setName(name);
                column.setDataType(type);
                column.setConstraint(constraint);
                column.setComment(comment);

                columns.add(column);
            }
            table.setColumns(columns);
        }
        result.close();
        con.close(); // 4、关闭数据库
        return tables;
    }



    public static void main(String[] args) {
        try {
            List<Table> tables = TableHandler.getTables();
            for (Table table : tables){
                System.out.println(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


