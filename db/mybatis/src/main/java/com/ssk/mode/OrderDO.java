package com.ssk.mode;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/9
 */
@Data
@Table(name = "my_order")
public class OrderDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "amount")
    private Long amount;

}
