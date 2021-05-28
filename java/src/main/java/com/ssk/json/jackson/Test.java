package com.ssk.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/5/16
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException {

        ClsRectangle clsRectangle = new ClsRectangle(1,2);
        a(clsRectangle);





//        ClsRectangle rectangle = new ClsRectangle(7,9); //构建正方形对象
//        ClsCircle circle = new ClsCircle(8); //构建长方形对象
//        List<ClsShape> shapes = new ArrayList<>();  //List<多种形状>
//        shapes.add(circle);
//        shapes.add(rectangle);
//        ClsView view = new ClsView();  //将List放入画面View
//        view.setShapes(shapes);
//
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println("-- 序列化 --");
//        String jsonStr = mapper.writeValueAsString(view);
//        System.out.println(jsonStr);
//
//        System.out.println("-- 反序列化 --");
//        ClsView deserializeView = mapper.readValue(jsonStr, ClsView.class);
//        System.out.println(deserializeView);

    }


    public static void a(ClsShape clsShape) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(clsShape));
        ClsRectangle clsShape1 = (ClsRectangle) clsShape;
        System.out.println(clsShape1.getHeight());
    }
}
