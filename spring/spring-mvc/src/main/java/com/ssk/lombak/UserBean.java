package com.ssk.lombak;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.beans.ConstructorProperties;

/**
 * @author ssk
 * @date 2020/9/24
 */
@Data
@ToString(callSuper = true)
@Builder
public class UserBean {
//
//    private Long id;

    private String userName;

    private Integer age;


    //@ConstructorProperties()
    public UserBean( String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }
}
