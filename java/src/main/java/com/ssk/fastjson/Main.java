package com.ssk.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.math.BigDecimal;

/**
 * @author ssk
 * @date 2021/1/19
 */
public class Main {

    public static void main(String[] args) {
        Store store = new Store();
        store.setName("Hollis");
        Apple apple = new Apple();
        apple.setPrice(new BigDecimal(0.5));
        store.setFruit(apple);
        String jsonString = JSON.toJSONString(store, SerializerFeature.WriteClassName);
        System.out.println("toJSONString : " + jsonString);

        Store newStore = JSON.parseObject(jsonString, Store.class);
        System.out.println("parseObject : " + newStore);
        Apple newApple = (Apple)newStore.getFruit();
        System.out.println("getFruit : " + newApple);
    }
}
