package com.j2ee.yummy.model.order;

import com.j2ee.yummy.yummyEnum.OrderState;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: yummy
 * @description: 用作定时的Order
 * @author: Liu Hanyi
 * @create: 2019-03-02 16:37
 **/

@Getter
@Setter
public class MessageOrder {
    private long orderID;
    private OrderState orderState;
    //单位分钟
    private int remainingTime;

    public MessageOrder() {
    }

    public MessageOrder(long orderID, OrderState orderState, int remainingTime) {
        this.orderID = orderID;
        this.orderState = orderState;
        this.remainingTime = remainingTime;
    }
}
