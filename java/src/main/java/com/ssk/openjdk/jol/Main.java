package com.ssk.openjdk.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/3/19
 */
public class Main {

    public static void main(String[] args) {
//        Main main = new Main();
//        //  使用jol计算对象的大小（单位字节）
//        long instanceSize = ClassLayout.parseInstance(main).instanceSize();
//        System.out.println("对象头的大小"+instanceSize);
//
//        // 使用jol查看对象的内存布局
//        String toPrintable = ClassLayout.parseInstance(main).toPrintable();
//        System.out.println("对象布局:\n" + toPrintable);
//
//
//        User user = new User();
//
//
//        Object obj = generate();
//
//        //查看对象内部信息
//        print(ClassLayout.parseInstance(obj).toPrintable());
//
//        //查看对象外部信息
//        print(GraphLayout.parseInstance(obj).toPrintable());
//
//        //获取对象总大小
//        print("size : " + GraphLayout.parseInstance(obj).totalSize());

        final Main a = new Main();

        ClassLayout layout = ClassLayout.parseInstance(a);

        System.out.println("**** Fresh object");
        System.out.println(layout.toPrintable());

        synchronized (a) {
            System.out.println("**** With the lock");
            System.out.println(layout.toPrintable());
        }

        System.out.println("**** After the lock");
        System.out.println(layout.toPrintable());



    }

    static Object generate() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Integer(1));
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

    static void print(String message) {
        System.out.println(message);
        System.out.println("-------------------------");
    }




















}
