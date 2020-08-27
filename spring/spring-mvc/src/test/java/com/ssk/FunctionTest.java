package com.ssk;

import java.util.function.Function;

/**
 * @author ssk
 * @date 2020/8/20
 */
public class FunctionTest {

    public static void main(String[] sr){
        Function<Integer,Integer> a = i->i+1;
        Function<String,String> b =  c->c+"S";

        System.out.println(a.apply(2));
        System.out.println(b.apply("S"));

    }
}
