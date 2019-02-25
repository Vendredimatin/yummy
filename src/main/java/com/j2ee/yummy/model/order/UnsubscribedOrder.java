package com.j2ee.yummy.model.order;

import com.j2ee.yummy.model.order.Order;

/**
 * @program: yummy
 * @description: 退订的订单类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:47
 **/

public class UnsubscribedOrder extends Order {

    private String reason;
    private UnsubscribeRule unsubscribeRule;
}
