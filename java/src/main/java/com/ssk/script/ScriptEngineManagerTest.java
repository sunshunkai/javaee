package com.ssk.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author 孙顺凯（惊落）
 * @date 2021/3/31
 */
public class ScriptEngineManagerTest {


    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine js = manager.getEngineByName("js");

        String str = "a > 3 && b < 10 ";
        js.put("a",2);
        js.put("b",2);

        Object eval = js.eval(str);
        System.out.println(eval);
    }

}
