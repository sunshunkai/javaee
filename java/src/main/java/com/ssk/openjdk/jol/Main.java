package com.ssk.openjdk;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author ssk
 * @date 2021/3/19
 */
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        //  使用jol计算对象的大小（单位字节）
        long instanceSize = ClassLayout.parseInstance(main).instanceSize();
        System.out.println("对象头的大小"+instanceSize);

        // 使用jol查看对象的内存布局
        String toPrintable = ClassLayout.parseInstance(main).toPrintable();
        System.out.println("对象布局:\n" + toPrintable);
    }





















}
