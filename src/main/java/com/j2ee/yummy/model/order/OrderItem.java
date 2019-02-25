package com.j2ee.yummy.model.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: yummy
 * @description: 订单表项类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:51
 **/
@Getter
@Setter
public class OrderItem {

    private long id;
    private long orderID;
    private String name;
    private int num;
    private double price;
    private double subtotal;

    public OrderItem() {
    }
}
