package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.OrderItemDao;
import com.j2ee.yummy.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: yummy
 * @description: orderItem的业务逻辑类
 * @author: Liu Hanyi
 * @create: 2019-03-01 23:02
 **/

@Service
public class OrderItemServiceImpl {
    @Autowired
    OrderItemDao orderItemDao;

    public OrderItem save(OrderItem orderItem){
        return orderItemDao.insert(orderItem);
    }

}
