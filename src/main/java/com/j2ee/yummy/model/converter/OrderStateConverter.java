package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @program: yummy
 * @description: OrderState的转换类
 * @author: Liu Hanyi
 * @create: 2019-03-01 22:46
 **/
@Converter(autoApply = true)
public class OrderStateConverter implements AttributeConverter<OrderState,String> {
    @Override
    public String convertToDatabaseColumn(OrderState orderState) {
        String s = JSON.toJSONString(orderState);
        return s;
    }

    @Override
    public OrderState convertToEntityAttribute(String s) {
        OrderState orderState = JSON.parseObject(s, OrderState.class);
        return orderState;
    }
}
