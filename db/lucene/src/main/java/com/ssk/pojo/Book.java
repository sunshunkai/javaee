package com.ssk.pojo;

import lombok.Data;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/20
 */
@Data
public class Book {

    private Integer id;
    // 图书名称
    private String name;
    // 图书价格
    private Float price;
    // 图书描述
    private String desc;

}
