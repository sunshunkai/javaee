package com.ssk.file;

import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ssk
 * @date 2021/1/27
 */
public class poiUse {

    public static void main(String[] args){

        ConversionClass conversionClass = new ConversionClass();

        List<ResultObject> result = Lists.newArrayList();

        Class clasz = conversionClass.getClass();

        Field[] fields = clasz.getDeclaredFields();

        ResultObject firstObject = new ResultObject();
        firstObject.setField("参数");
        firstObject.setFill("是否必填");
        firstObject.setRemark("描述");
        firstObject.setType("类型");
        result.add(firstObject);
        for (Field field : fields){
            if(field.getName().equals("serialVersionUID")){
                continue;
            }
            ResultObject resultObject = new ResultObject();
            resultObject.setFill("是");
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof ApiModelProperty){
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    resultObject.setRemark(apiModelProperty.value());

                }
            }
            resultObject.setField(field.getName());
            resultObject.setType(getType(field.getType().getTypeName()));
            result.add(resultObject);
        }
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("WriterDataTest");
            int rowNum = 0;

            for (ResultObject object : result){
                Row row = sheet.createRow(rowNum++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(object.getField());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(object.getType());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(object.getFill());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(object.getRemark());
            }
            File file = new File("/Users/ssk/Desktop/file.xlsx");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);


        }catch (Exception e){

        }finally {

        }
    }

    private static String getType(String comType){
        String[] types = comType.split("\\.");

        int len = types.length;

        return types[len-1];
    }



    public static void check(Class clasz){


        List<ResultObject> result = Lists.newArrayList();


        ResultObject firstObject = new ResultObject();
        firstObject.setField("参数");
        firstObject.setFill("是否必填");
        firstObject.setRemark("描述");
        firstObject.setType("类型");
        result.add(firstObject);

        // 除了当前类的字段,包含复合对象
        Field[] fields = clasz.getDeclaredFields();
        fields(fields,result);






        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("WriterDataTest");
            int rowNum = 0;

            for (ResultObject object : result){
                Row row = sheet.createRow(rowNum++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(object.getField());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(object.getType());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(object.getFill());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(object.getRemark());
            }
            File file = new File("/Users/ssk/Desktop/file.xlsx");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
        }catch (Exception e){

        }
    }
    
    
    private static void fields(Field[] fields,List<ResultObject> result){
        for (Field field : fields) {

            if(field.getName().equals("serialVersionUID")){
                continue;
            }

            ResultObject resultObject = new ResultObject();
            resultObject.setFill("是");
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof ApiModelProperty){
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    resultObject.setRemark(apiModelProperty.value());

                }
            }
            resultObject.setField(field.getName());
            resultObject.setType(getType(field.getType().getTypeName()));
            result.add(resultObject);
        }
    }

}
