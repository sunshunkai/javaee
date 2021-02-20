package com.ssk.pattern.bridge;

/**
 * @author ssk
 * @date 2021/1/17
 * 所有远程控制器的通用接口
 */
public interface Remote {
    void power();

    void volumeDown();

    void volumeUp();

    void channelDown();

    void channelUp();
}
