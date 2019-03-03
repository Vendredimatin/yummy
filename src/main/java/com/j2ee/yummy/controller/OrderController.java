package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.SpringTaskDemo;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.model.order.MessageOrder;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.OrderItem;
import com.j2ee.yummy.service.AddressService;
import com.j2ee.yummy.service.MemberService;
import com.j2ee.yummy.serviceImpl.ComboServiceImpl;
import com.j2ee.yummy.serviceImpl.DishServiceImpl;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.yummyEnum.ItemCategory;
import com.j2ee.yummy.yummyEnum.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @program: yummy
 * @description: 订单的controller类
 * @author: Liu Hanyi
 * @create: 2019-02-25 10:12
 **/
@Controller
public class OrderController {
    @Autowired
    MemberService memberService;
    @Autowired
    AddressService addressService;
    @Autowired
    DishServiceImpl dishService;
    @Autowired
    ComboServiceImpl comboService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    SpringTaskDemo springTaskDemo;

    @GetMapping(value = "/memberOrderDisplay")
    public String init(){
        return "memberOrder.html";
    }

    @GetMapping(value = "/canteenOrderHistory")
    public String intiCanteenOrder(){ return "canteenOrder.html";}

    @PostMapping(value = "/member/order/checkout")
    @ResponseBody
    public Object checkout(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        System.out.println("进入 OrderController checkcout.......");

        long memberID = (long) httpSession.getAttribute("memberID");
        long canteenID = (long) httpSession.getAttribute("checkoutCanID");
        int deliveringTime = 2;

        Member member = memberService.getMemberByID(memberID);
        String memberName = member.getName();
        String memberPhone = member.getPhone();

        long addressID = jsonObject.getLong("addressID");
        Address address = addressService.getAddressByID(addressID);

        LocalDateTime time = LocalDateTime.now();
        double totalPrice = jsonObject.getDouble("totalPrice");
        OrderState orderState = OrderState.未支付;

        Order order = new Order();
        order.setMemberID(memberID);
        order.setCanteenID(canteenID);
        order.setMemberName(memberName);
        order.setMemberPhone(memberPhone);
        order.setMemberAddress(address);
        order.setTime(time);
        order.setTotalPrice(totalPrice);
        order.setOrderState(orderState);
        order.setDeliveringTime(deliveringTime);

        List<Long> dishIDs = jsonObject.getJSONArray("dishIDs").toJavaList(Long.class);
        List<Long> comboIDs = jsonObject.getJSONArray("dishIDs").toJavaList(Long.class);
        List<Integer> dishQuantities = jsonObject.getJSONArray("dishQuantities").toJavaList(Integer.class);
        List<Integer> comboQuantities = jsonObject.getJSONArray("comboQuantities").toJavaList(Integer.class);
        List<Double> dishSubtotals = jsonObject.getJSONArray("dishSubtotals").toJavaList(Double.class);
        List<Double> comboSubtotals = jsonObject.getJSONArray("comboSubtotals").toJavaList(Double.class);

        List<Dish> dishes = dishService.getDishesByIDs(dishIDs);
        List<Combo> combos = comboService.getCombosByIDs(comboIDs);

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setItemID(dish.getId());
            orderItem.setName(dish.getName());
            orderItem.setItemCategory(ItemCategory.Dish);
            orderItem.setNum(dishQuantities.get(i));
            orderItem.setPrice(dish.getPrice());
            orderItem.setSubtotal(dishSubtotals.get(i));
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        for (int i = 0; i < combos.size(); i++) {
            Combo combo = combos.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setItemID(combo.getId());
            orderItem.setName(combo.getName());
            orderItem.setItemCategory(ItemCategory.Combo);
            orderItem.setNum(comboQuantities.get(i));
            orderItem.setPrice(combo.getPrice());
            orderItem.setSubtotal(comboSubtotals.get(i));
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(new HashSet<>(orderItems));

        order = orderService.checkout(order);
        System.out.println(order);
        springTaskDemo.appendOrder(order);

        httpSession.setAttribute("unpayedOrderID",order.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "下单成功");
        return map;
    }

    @PostMapping(value = "/member/order/history")
    @ResponseBody
    public List<Order> memberHistory(HttpSession session){
        System.out.println("进入 OrderController history....................");

        long memberID = (long) session.getAttribute("memberID");

        List<Order> orders = orderService.getOrdersByMemID(memberID);

        return orders;
    }

    @PostMapping(value = "/canteen/order/history")
    @ResponseBody
    public List<Order> canteenHistory(HttpSession session){
        System.out.println("进入 OrderController canteenHistory....................");

        long canteenID = (long) session.getAttribute("canteenID");

        List<Order> orders = orderService.getOrdersByCanID(canteenID);

        return orders;
    }


    @PostMapping(value = "/member/order/pay")
    @ResponseBody
    public Object pay(@RequestBody JSONObject jsonObject,HttpSession session){
        System.out.println("进入 OrderController pay....................");

        long unpayedOrderID = (long) session.getAttribute("unpayedOrderID");
        springTaskDemo.changeOrderState(unpayedOrderID,OrderState.派送中);
        orderService.pay(unpayedOrderID);


        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "支付成功");
        return map;
    }


    @PostMapping(value = "/member/order/unsubscribe")
    @ResponseBody
    public Object unsubscribe(@RequestBody JSONObject jsonObject){
        System.out.println("进入 OrderController unsubscribe....................");

        long orderID = jsonObject.getLong("orderID");

        orderService.unsubscribe(orderID);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "退订成功");
        return map;
    }


}
