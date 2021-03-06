package com.ssk.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2021/3/4
 */
@Component
@ConditionalOnProperty(name = "sku.auto.off.shelf.enable", havingValue = "true", matchIfMissing = true)
public class MyConguration {
    public MyConguration(){
        System.out.println("bean加载-----MyConguration");
    }
}
