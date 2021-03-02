package com.ssk.batch;

import lombok.Data;

import java.util.Date;

/**
 * @author ssk
 * @date 2021/2/28
 */
@Data
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
}
