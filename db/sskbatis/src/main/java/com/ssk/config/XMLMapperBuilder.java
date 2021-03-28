package com.ssk.config;

import com.ssk.pojo.Configuration;
import com.ssk.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document read = new SAXReader().read(inputStream);
        Element rootElement = read.getRootElement();
        List<Element> list = rootElement.selectNodes("//select");

        String namespace = rootElement.attributeValue("namespace");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sqlText);

            String key = namespace +"." +id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }
}
