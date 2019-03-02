package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.OrderRepository;
import com.j2ee.yummy.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: order的dao类
 * @author: Liu Hanyi
 * @create: 2019-03-01 23:01
 **/
@Repository
public class OrderDao {
    @Autowired
    OrderRepository orderRepository;

    public Order insert(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByMemID(long memberID){
        return orderRepository.findAllByMemberID(memberID);
    }
}
