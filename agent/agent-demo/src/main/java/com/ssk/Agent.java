package com.ssk;

import java.lang.instrument.Instrumentation;

/**
 * @author 惊云
 * @date 2021/12/9 9:01
 */
public class Agent {

    public static void premain(String args, Instrumentation inst){
        System.out.println("Hi, I'm agent!");
//        inst.addTransformer(new TestTransformer());
    }


}
