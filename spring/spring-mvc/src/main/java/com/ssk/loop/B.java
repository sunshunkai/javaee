package com.ssk.loop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2020/9/17
 */
@Component
public class B {

    @Autowired
    private A a;
}
