package com.ssk.gc;

/**
 * @author ssk
 * @date 2020/12/4
 *
 * -XX:+DoEscapeAnalysis   开启逃逸分析
 * -XX:+PrintGCDetails   打印GC详情
 *
 *
 * 同步省略（锁消除）
 * 将堆分配转化为栈分配
 * 分离对象或标量替换
 */
public class EscapeAnalysisDemo {

    public static void main(String[] args) {

        while (true){
            apply();
        }
    }

    private static void apply(){
        new A();
    }




    public static class A{
        private Long a;
        private Long b;
        private Long c;
        private Long d;
        private Long e;
        private Long f;
    }

}
