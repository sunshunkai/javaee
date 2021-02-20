package com.ssk.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/1/15
 */
public class Main {

    public static void main(String[] args) throws IOException, TemplateException, IOException {

        // 1.创建一个配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());

        // 2.设置模版所在的目录（刚刚Template File所在目录）抛IOException

        configuration.setDirectoryForTemplateLoading(new File("/Users/ssk/Documents/ssk/code/javaee/tool/freemark/src/test/resources"));

        // 3.设置字符集
        configuration.setDefaultEncoding("utf-8");

        // 4.获取模版对象

        Template template = configuration.getTemplate("html.ftl");

        // 5.创建数据模型（对象 / map）
        Map map = new HashMap();
        map.put("name","张三");
        map.put("message","Welcome!!!");

        // 6.创建一个输出流对象
        Writer out = new FileWriter("/Users/ssk/Desktop/test.docx");

        // 7.最关键：通过模版文件输出（根据map数据输出到out输出流）
        template.process(map,out); // 抛TemplateException

        // 8.关闭
        out.close();
    }
}
