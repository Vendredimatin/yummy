package com.j2ee.yummy;

import com.j2ee.yummy.model.order.MessageOrder;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.j2ee.yummy.StaticFinalVariable.MINUTE;
import static com.j2ee.yummy.StaticFinalVariable.PAY_TIME;

/**
 * @program: yummy
 * @description: taskDemo
 * @author: Liu Hanyi
 * @create: 2019-03-02 16:28
 **/
@Component
public class SpringTaskDemo {
    @Autowired
    OrderServiceImpl orderService;

    private static final Logger log = LoggerFactory.getLogger(SpringTaskDemo.class);

    private static List<MessageOrder> messageOrders = new ArrayList<>();
    private static List<MessageOrder> unpaidOrders = new ArrayList<>();
    private static List<MessageOrder> deliveringOrders = new ArrayList<>();

    public SpringTaskDemo() {
    }

    @Async
    @Scheduled(fixedRate = 1000 * MINUTE/6)
    public void scheduled2() throws InterruptedException {
        for (int i = 0; i < messageOrders.size(); i++) {
            MessageOrder messageOrder = messageOrders.get(i);
            if (messageOrder.getRemainingTime() == 0) {
                //service
                messageOrders.remove(i--);
                if (messageOrder.getOrderState() == OrderState.未支付) {
                    cancel(messageOrder.getOrderID());
                    log.info("目前时间：" + LocalDateTime.now() + "订单" + messageOrder.getOrderID() + "时间到未支付取消", LocalDateTime.now());
                } else{
                    confirm(messageOrder.getOrderID());
                    log.info("目前时间：" + LocalDateTime.now() + "订单" + messageOrder.getOrderID() + "已派送到", LocalDateTime.now());
                }
            } else {
                messageOrder.setRemainingTime(messageOrder.getRemainingTime() - 1 * MINUTE/6);
                messageOrders.set(i, messageOrder);
            }
        }
        log.info("现在时间：" + LocalDateTime.now(), LocalDateTime.now());
    }

    public void appendOrder(MessageOrder messageOrder) {
        messageOrders.add(messageOrder);
    }

    public void appendOrder(Order order) {
        MessageOrder messageOrder = new MessageOrder();
        messageOrder.setOrderID(order.getId());
        messageOrder.setOrderState(order.getOrderState());
        if (order.getOrderState() == OrderState.未支付)
            messageOrder.setRemainingTime(PAY_TIME);
        else if (order.getOrderState() == OrderState.派送中)
            messageOrder.setRemainingTime(order.getDeliveringTime() * MINUTE);

        messageOrders.add(messageOrder);
        log.info("添加order成功" , LocalDateTime.now());
    }

    public void removeOrder(long orderID){
        for (int i = 0; i < messageOrders.size(); i++) {
            MessageOrder messageOrder = messageOrders.get(i);
            if (messageOrder.getOrderID() == orderID){
                messageOrders.remove(i);
                break;
            }
        }
        log.info("提前确认order成功" , LocalDateTime.now());
    }

    public void changeOrderState(long orderID,OrderState orderState){
        for (int i = 0; i < messageOrders.size(); i++) {
            MessageOrder messageOrder = messageOrders.get(i);
            if (messageOrder.getOrderID() == orderID){
                messageOrder.setOrderState(orderState);
                messageOrders.set(i,messageOrder);
                break;
            }
        }
        log.info("改变order成功" , LocalDateTime.now());
    }

    public void unsubscribeOrder(long orderID){
        for (int i = 0; i < messageOrders.size(); i++) {
            if (messageOrders.get(i).getOrderID() == orderID){
                messageOrders.remove(i);
                break;
            }
        }

        log.info("退订order成功" , LocalDateTime.now());
    }

    public void pay(long orderID) {
        MessageOrder order = null;
        for (int i = 0; i < unpaidOrders.size(); i++) {
            order = unpaidOrders.get(i);
            if (order.getOrderID() == orderID) {
                unpaidOrders.remove(order);
                break;
            }
        }

        order.setOrderState(OrderState.派送中);
        deliveringOrders.add(order);
    }

    public void cancel(long orderID) {
        orderService.cancel(orderID);
    }

    public void confirm(long orderID) {
        orderService.confirm(orderID);
    }

}
