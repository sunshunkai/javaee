package com.ssk.pattern.bridge;

/**
 * @author ssk
 * @date 2021/1/17
 * 高级远程控制器
 */
public class AdvancedRemote extends BasicRemote {

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
