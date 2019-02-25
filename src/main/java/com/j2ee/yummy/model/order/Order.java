package com.j2ee.yummy.model.order;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.yummyEnum.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @program: yummy
 * @description: 订单类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:42
 **/
@Getter
@Setter
public class Order {

    protected long id;
    protected long customerID;
    protected long canteenID;
    protected String name;
    protected String phone;
    protected Address address;
    protected LocalDate date;
    protected double totalPrice;
    protected OrderState orderState;

}
