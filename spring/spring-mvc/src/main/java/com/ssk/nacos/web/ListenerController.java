package com.ssk.nacos.web;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ssk
 * @date 2021/3/8
 */
//@RestController("listener")
public class ListenerController {
    
    private static final String DATA_ID = "example";
    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";


    @NacosInjected
    private ConfigService configService;

    @RequestMapping("publish-config")
    public boolean publishConfig() throws NacosException {

        // 等价于 NacosConfigListener
        configService.addListener(DATA_ID, DEFAULT_GROUP, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String config) {
                System.out.println("nacos listener1 :" +config);
            }
        });

        return configService.publishConfig(DATA_ID, DEFAULT_GROUP, "9527");
    }

    @NacosConfigListener(dataId = DATA_ID)
    public void onMessage(String config) {
        System.out.println("nacos listener2 :" +config);
    }
    

}
