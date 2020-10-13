package com.ssk.mode;

import lombok.Data;

/**
 * @author ssk
 * @date 2020/8/21
 */
@Data
public class UserDO {

    private Long id;
    private String userName;
    private String password;
    private Byte deleted;
    private Integer version;

}
