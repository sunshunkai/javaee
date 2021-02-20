package com.ssk.fastjson;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ssk
 * @date 2021/1/19
 */
@Data
public class Apple implements Fruit{
    private BigDecimal price;
}
