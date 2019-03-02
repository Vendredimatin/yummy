package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.OrderDao;
import com.j2ee.yummy.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: yummy
 * @description: 订单的业务逻辑类
 * @author: Liu Hanyi
 * @create: 2019-03-01 23:01
 **/

@Service
public class OrderServiceImpl {
    @Autowired
    OrderDao orderDao;

    public Order save(Order order){
        return orderDao.insert(order);
    }

    public List<Order> getOrdersByMemID(long memberID){
        return orderDao.getOrdersByMemID(memberID);
    }
}
