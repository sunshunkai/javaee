package com.ssk;

import com.sun.tools.javac.util.Name;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/1/15
 */
public class WordHandler {

    private Configuration configuration = null;

    public WordHandler() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public void data2word( Map<String,Object> data) {
        // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以servlet，classpath，数据库装载，
        // 这里我们的模板是放在org.cnzjw.template包下面
        configuration.setClassForTemplateLoading(this.getClass(),
                "/");
        Template t = null;
        try {
            // word.ftl为要装载的模板
            t = configuration.getTemplate("table.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出文档路径及名称
        File outFile = new File("/Users/ssk/Desktop/item结构_copy.docx");
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(data, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        WordHandler wh = new WordHandler();
        List<Table> tables= TableHandler.getTables();//获取表结构数据
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("tables",tables);
        wh.data2word(data);
    }

}
