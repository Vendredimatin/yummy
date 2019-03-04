package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.BalanceDao;
import com.j2ee.yummy.dao.OrderDao;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
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

    public static final double CAN_DISCOUNT = 0.8;
    public static final double YUMMY_DISCOUNT = 1 - CAN_DISCOUNT;
    public static final double RETURN_DISCOUNT = 0.8;

    public Order checkout(Order order) {
        return orderDao.insert(order);
    }

    public List<Order> getOrdersByMemID(long memberID) {
        return orderDao.getOrdersByMemID(memberID);
    }

    public List<Order> getOrdersByCanID(long canteenID) {
        return orderDao.getOrdersByCanID(canteenID);
    }

    /**
     * 支付后，钱会先到平台上，确认后才会分给商家
     *
     * @param orderID
     * @return
     */
    public boolean pay(long orderID) {
        Order order = orderDao.getOrderByID(orderID);

        Balance memberBalance = balanceDao.getBalance(order.getMemberID(), UserType.Member);
        Balance yummyBalance = balanceDao.getBalance(0);


        memberBalance.setBalance(memberBalance.getBalance() - order.getTotalPrice());
        yummyBalance.setBalance(yummyBalance.getBalance() + order.getTotalPrice());
        //yummy结算
        balanceDao.updateBalance(memberBalance);
        balanceDao.updateBalance(yummyBalance);

        return orderDao.updateOrderState(OrderState.派送中, orderID);
    }

    public void cancel(long orderID) {
        orderDao.updateOrderState(OrderState.取消, orderID);
    }

    /**
     * 确认订单，资金会由yummy和canteen分成
     *
     * @param orderID
     */
    @Transactional
    public void confirm(long orderID) {
        orderDao.updateOrderState(OrderState.完成, orderID);

        Order order = orderDao.getOrderByID(orderID);
        long canteenID = order.getCanteenID();
        double price = order.getTotalPrice();

        Balance canteenBalance = balanceDao.getBalance(canteenID, UserType.Canteen);
        Balance yummyBalance = balanceDao.getBalance(0);

        canteenBalance.setBalance(canteenBalance.getBalance() + price * CAN_DISCOUNT);
        yummyBalance.setBalance(yummyBalance.getBalance() - price * CAN_DISCOUNT);
        //yummy结算
        balanceDao.updateBalance(yummyBalance);
        balanceDao.updateBalance(canteenBalance);
    }

    @Transactional
    public double unsubscribe(long orderID) {
        Order order = orderDao.getOrderByID(orderID);
        //顾客退80%的费用
        double returnFee = order.unsubscribe();

        orderDao.updateOrderState(OrderState.退货, orderID);
        Balance memberBalance = balanceDao.getBalance(order.getMemberID(), UserType.Member);
        Balance canteenBalance = balanceDao.getBalance(order.getCanteenID(), UserType.Canteen);
        Balance yummyBalance = balanceDao.getBalance(0);

        //先从账户上扣完全款
        yummyBalance.setBalance(yummyBalance.getBalance() - order.getTotalPrice());
        //会员退80%
        memberBalance.setBalance(memberBalance.getBalance() + returnFee);
        //餐厅得到剩下的80%
        canteenBalance.setBalance(canteenBalance.getBalance() + order.getTotalPrice()*(1-RETURN_DISCOUNT) * CAN_DISCOUNT);
        //平台分成
        yummyBalance.setBalance(yummyBalance.getBalance() + order.getTotalPrice()*(1-RETURN_DISCOUNT) * YUMMY_DISCOUNT);
        //yummy结算
        balanceDao.updateBalance(memberBalance);
        balanceDao.updateBalance(canteenBalance);

        return returnFee;
    }
}
