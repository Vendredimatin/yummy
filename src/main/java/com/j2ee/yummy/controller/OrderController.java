package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.SpringTaskDemo;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.OrderItem;
import com.j2ee.yummy.service.AddressService;
import com.j2ee.yummy.service.MemberService;
import com.j2ee.yummy.serviceImpl.CanteenServiceImpl;
import com.j2ee.yummy.serviceImpl.ComboServiceImpl;
import com.j2ee.yummy.serviceImpl.DishServiceImpl;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.yummyEnum.ItemCategory;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.j2ee.yummy.StaticFinalVariable.PAGE_SIZE;

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
    @Autowired
    CanteenServiceImpl canteenService;

    @GetMapping(value = "/memberOrderDisplay")
    public String init(){
        return "memberOrder.html";
    }

    @GetMapping(value = "/canteenOrderHistory")
    public String intiCanteenOrder(){ return "canteenOrder.html";}

    @GetMapping(value = "/canteenOrderDetail")
    public String intiCanOrderDetail(){ return "canteenOrderDetail.html";}

    @GetMapping(value = "/memberOrderHistory")
    public String intiMemOrderDetail() {
        return "memberOrderDetail.html";
    }

    //下订单
    @PostMapping(value = "/member/order/checkout")
    @ResponseBody
    public Object checkout(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        System.out.println("进入 OrderController checkcout.......");

        long memberID = (long) httpSession.getAttribute("memberID");
        long canteenID = (long) httpSession.getAttribute("checkoutCanID");

        Member member = memberService.getMemberByID(memberID);
        String memberName = member.getName();
        String memberPhone = member.getPhone();

        Canteen canteen = canteenService.getCanteenByID(canteenID);
        String canteenName = canteen.getCanteenName();

        int deliveringTime = 2;//jsonObject.getInteger("deliveringTime");

        long addressID = jsonObject.getLong("addressID");
        Address address = addressService.getAddressByID(addressID);

        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        double totalPrice = jsonObject.getDouble("totalPrice");
        OrderState orderState = OrderState.未支付;

        Order order = new Order();
        order.setMemberID(memberID);
        order.setCanteenID(canteenID);
        order.setMemberName(memberName);
        order.setCanteenName(canteenName);
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
    public Object memberHistory(HttpSession session){
        System.out.println("进入 OrderController history....................");

        long memberID = (long) session.getAttribute("memberID");

        List<Order> orders = orderService.getOrdersByMemID(memberID);

        int size = (orders.size()>=PAGE_SIZE)?PAGE_SIZE:orders.size();

        Map<String,Object> map = new HashMap<>();
        map.put("memberName",session.getAttribute("memberName"));
        map.put("orders",orders.subList(0,size));
        return map;
    }

    @PostMapping(value = "/canteen/order/history")
    @ResponseBody
    public Object canteenHistory(HttpSession session){
        System.out.println("进入 OrderController canteenHistory....................");

        long canteenID = (long) session.getAttribute("canteenID");

        List<Order> orders = orderService.getOrdersByCanID(canteenID);

        int size = (orders.size()>=PAGE_SIZE)?PAGE_SIZE:orders.size();
        Map<String,Object> map = new HashMap<>();
        map.put("canteenName",session.getAttribute("canteenName"));
        map.put("orders",orders.subList(0,size));
        return map;
    }


    @PostMapping(value = "/member/order/pay")
    @ResponseBody
    public Object pay(@RequestBody JSONObject jsonObject,HttpSession session){
        System.out.println("进入 OrderController pay....................");

        long unpayedOrderID = (long) session.getAttribute("unpayedOrderID");
        springTaskDemo.changeOrderState(unpayedOrderID,OrderState.派送中);
        orderService.pay(unpayedOrderID);
        Map<String,Object> map = orderService.pay(unpayedOrderID);
        map.put("success", true);
        map.put("message", "支付成功");

        return map;
    }


    @PostMapping(value = "/member/order/unsubscribe")
    @ResponseBody
    public Object unsubscribe(@RequestBody JSONObject jsonObject){
        System.out.println("进入 OrderController unsubscribe....................");

        long orderID = jsonObject.getLong("orderID");

        double returnFee = orderService.unsubscribe(orderID);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "退订成功");
        map.put("returnFee", returnFee);
        return map;
    }

    @PostMapping(value = "/member/order/search")
    @ResponseBody
    public List<Order> memberSearch(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 OrderController memberSearch....................");

        long memberID = (long) session.getAttribute("memberID");
        LocalDate startTime = jsonObject.getObject("startTime",LocalDate.class);
        LocalDate endTime = jsonObject.getObject("endTime",LocalDate.class);
        double maxPrice = jsonObject.getObject("maxPrice",Double.class);
        double minPrice = jsonObject.getObject("minPrice",Double.class);
        String canteenName = jsonObject.getString("canteenName");
        String orderState = jsonObject.getString("orderState");

        Page<Order> orders = orderService.memberSearch(memberID,startTime,endTime,maxPrice,minPrice,canteenName,orderState,1);

        return orders.getContent();
    }

    @PostMapping(value = "/member/order/page")
    @ResponseBody
    public List<Order> memberPage(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 OrderController memberSearch....................");

        long memberID = (long) session.getAttribute("memberID");
        LocalDate startTime = jsonObject.getObject("startTime",LocalDate.class);
        LocalDate endTime = jsonObject.getObject("endTime",LocalDate.class);
        double maxPrice = jsonObject.getObject("maxPrice",Double.class);
        double minPrice = jsonObject.getObject("minPrice",Double.class);
        String canteenName = jsonObject.getString("canteenName");
        String orderState = jsonObject.getString("orderState");
        int pageIndex = jsonObject.getInteger("nextPage");

        Page<Order> orders = orderService.memberSearch(memberID,startTime,endTime,maxPrice,minPrice,canteenName,orderState,pageIndex);

        return orders.getContent();
    }

    @PostMapping(value = "/canteen/order/search")
    @ResponseBody
    public List<Order> canteenSearch(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 OrderController canteenSearch....................");

        long canteenID = (long) session.getAttribute("canteenID");
        LocalDate startTime = jsonObject.getObject("startTime",LocalDate.class);
        LocalDate endTime = jsonObject.getObject("endTime",LocalDate.class);
        double maxPrice = jsonObject.getObject("maxPrice",Double.class);
        double minPrice = jsonObject.getObject("minPrice",Double.class);
        String memberName = jsonObject.getString("memberName");
        String orderState = jsonObject.getString("orderState");

        Page<Order> orders = orderService.canteenSearch(canteenID,startTime,endTime,maxPrice,minPrice,memberName,orderState,1);

        return orders.getContent();
    }

    @PostMapping(value = "/canteen/order/page")
    @ResponseBody
    public List<Order> canteenPage(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 OrderController canteenSearch....................");

        long canteenID = (long) session.getAttribute("canteenID");
        LocalDate startTime = jsonObject.getObject("startTime",LocalDate.class);
        LocalDate endTime = jsonObject.getObject("endTime",LocalDate.class);
        double maxPrice = jsonObject.getObject("maxPrice",Double.class);
        double minPrice = jsonObject.getObject("minPrice",Double.class);
        String memberName = jsonObject.getString("memberName");
        String orderState = jsonObject.getString("orderState");
        int pageIndex = jsonObject.getInteger("nextPage");

        Page<Order> orders = orderService.canteenSearch(canteenID,startTime,endTime,maxPrice,minPrice,memberName,orderState,pageIndex);

        return orders.getContent();
    }

    @PostMapping(value = "/order/detail/check")
    @ResponseBody
    public Object orderDetaile(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 OrderController detail....................");

        long orderID = jsonObject.getLong("orderID");
        session.setAttribute("orderID",orderID);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "支付成功");
        return map;
    }

    @PostMapping(value = "/order/detail/get")
    @ResponseBody
    public Order orderDetaile(HttpSession session){
        System.out.println("进入 OrderController detail....................");

        long orderID = (long) session.getAttribute("orderID");
        Order order = orderService.getOrderByID(orderID);
        return order;
    }

    @PostMapping(value = "/member/order/confirm")
    @ResponseBody
    public Object confirm(@RequestBody JSONObject jsonObject){
        System.out.println("进入 OrderController confirm....................");

        long orderID = jsonObject.getLong("orderID");

       // springTaskDemo.removeOrder(orderID);
        orderService.confirm(orderID);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "收货成功");
        return map;
    }


}
