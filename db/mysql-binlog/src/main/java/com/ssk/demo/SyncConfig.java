package com.ssk.demo;

import lombok.Data;

/**
 * @author 惊云
 * @date 2021/12/10 15:12
 */
@Data
public class SyncConfig {

    /**
     * mysql的host配置
     */
    String host;
    /**
     * mysql的port配置
     */
    Integer port;
    /**
     * mysql的userName配置
     */
    String userName;
    /**
     * mysql的password配置
     */
    String password;

}
