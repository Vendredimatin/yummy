package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.SpringTaskDemo;
import com.j2ee.yummy.dao.BalanceDao;
import com.j2ee.yummy.dao.OrderDao;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.yummyEnum.OrderState;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    BalanceDao balanceDao;

    public Order checkout(Order order) {
        return orderDao.insert(order);
    }

    public List<Order> getOrdersByMemID(long memberID) {
        return orderDao.getOrdersByMemID(memberID);
    }

    public List<Order> getOrdersByCanID(long canteenID){
        return orderDao.getOrdersByCanID(canteenID);
    }

    public boolean pay(long orderID) {

        return orderDao.updateOrderState(OrderState.派送中, orderID);
    }

    public void cancel(long orderID){
        //Order order = orderDao.getOrderByID(orderID);
        //order.setOrderState(OrderState.取消);
        //orderDao.update(order);
        orderDao.updateOrderState(OrderState.取消,orderID);
    }

    @Transactional
    public void confirm(long orderID){
        orderDao.updateOrderState(OrderState.完成,orderID);

        /*Order order = orderDao.getOrderByID(orderID);
        long memberID = order.getMemberID();
        long canteenID = order.getCanteenID();
        double price = order.getTotalPrice();

        Balance memberBalance = balanceDao.getBalance(memberID, UserType.Member);
        Balance canteenBalance = balanceDao.getBalance(canteenID,UserType.Member);
        Balance yummyBalance = balanceDao.getBalance(0);

        memberBalance.setBalance(memberBalance.getBalance()-price);
        canteenBalance.setBalance(canteenBalance.getBalance()+price);
        //yummy结算
        balanceDao.updateBalance(memberBalance);
        balanceDao.updateBalance(canteenBalance);*/
    }

    public void unsubscribe(long orderID){
        Order order = orderDao.getOrderByID(orderID);

    }
}
