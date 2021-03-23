package com.ssk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ssk
 * @date 2021/3/22
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 220401161187822517L;

    private Long id;

    private String name;

    private Integer sex;
}
