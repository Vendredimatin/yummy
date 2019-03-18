package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.Cart;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import com.j2ee.yummy.service.MemberService;
import com.j2ee.yummy.serviceImpl.BalanceServiceImpl;
import com.j2ee.yummy.serviceImpl.MemberServiceImpl;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @program: yummy
 * @description: 会员类的controller
 * @author: Liu Hanyi
 * @create: 2019-02-07 10:22
 **/

@Controller
public class MemberController {
    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    BalanceServiceImpl balanceService;
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/member/info")
    public String init() {
        return "memberInfo.html";
    }

    @GetMapping("/memberInfo")
    public String initInfo() {
        return "memberInfo.html";
    }

    @GetMapping("/memberBalance")
    public String initBalance() {
        return "memberBalance.html";
    }

    @PostMapping(value = "/member/info")
    @ResponseBody
    public Member getInfo(HttpSession session) {
        System.out.println("进入 getInfo............");

        long userID = (long) session.getAttribute("memberID");
        Member member = memberService.getMemberByID(userID);
        System.out.println(member);

        return member;
    }

    @PostMapping(value = "/member/modifyInfo")
    @ResponseBody
    public Object modifyInfo(@RequestBody JSONObject jsonObject, HttpSession session) {
        System.out.println("进入 modifyInfo............");

        String email = jsonObject.getString("email");
        String name = jsonObject.getString("name");
        String phone = jsonObject.getString("phone");

        long userID = (long) session.getAttribute("memberID");
        Member member = memberService.getMemberByID(userID);

        member.setEmail(email);
        member.setPhone(phone);
        member.setName(name);
        System.out.println(member);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "修改成功");
        map.put("success", true);
        return map;
    }

    @PostMapping(value = "/member/scanCanteen")
    @ResponseBody
    public Object scanCanteen(@RequestBody JSONObject jsonObject, HttpSession session) {
        System.out.println("进入 MemberController scanCanteen..........");

        long canteenID = jsonObject.getLong("canteenID");
        session.setAttribute("scanCanteenID", canteenID);

        Set<Cart> carts;
        //当会员从首页进入一家店时，应该声明一个cart
        if (Objects.isNull(session.getAttribute("carts"))){
            carts = new HashSet<>();
        }else carts = (Set<Cart>) session.getAttribute("carts");

        for (Cart c:carts) {
            if (c.getCanteenID() == canteenID){
                carts.remove(c);
                break;
            }
        }

        Cart cart = new Cart();
        cart.setCanteenID(canteenID);
        cart.setMemberID((Long) session.getAttribute("memberID"));
        carts.add(cart);

        session.setAttribute("carts",carts);

        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @PostMapping(value = "/member/balance/get")
    @ResponseBody
    public Object getBalance(HttpSession session) {
        System.out.println("进入 MemberController getBalance..........");

        long memberID = (long) session.getAttribute("memberID");

        Balance balance = balanceService.getBalance(memberID, UserType.Member);
        List<Order> orders = orderService.getOrdersByMemID(memberID);
        //double totalCost = orders.stream().filter(order -> order.getOrderState().equals(OrderState.完成)).mapToDouble(Order::getTotalPrice).sum();
        double totalCost = balanceService.getCost(memberID,UserType.Member);
        int totalOrderNums = (int) orders.stream().filter(order -> order.getOrderState().equals(OrderState.完成)).count();

        Map<String, Object> map = new HashMap<>();
        map.put("message", "获取成功");
        map.put("balance", balance);
        map.put("totalCost",totalCost);
        map.put("totalNums",totalOrderNums);
        map.put("memberName",session.getAttribute("memberName"));
        return map;
    }
}
