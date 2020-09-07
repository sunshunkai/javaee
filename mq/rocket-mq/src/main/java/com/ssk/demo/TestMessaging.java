package com.ssk.demo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ssk
 * @date 2020/8/27
 */
@Data
@Accessors(chain = true)
public class TestMessaging {
    private String msgId;
    private String msgText;
}
