package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.OrderItemRepository;
import com.j2ee.yummy.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: yummy
 * @description: orderItem的dao类
 * @author: Liu Hanyi
 * @create: 2019-03-01 23:02
 **/
@Repository
public class OrderItemDao {
    @Autowired
    OrderItemRepository orderItemRepository;

    public OrderItem insert(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }
}
