package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import com.j2ee.yummy.service.CanteenService;
import com.j2ee.yummy.serviceImpl.BalanceServiceImpl;
import com.j2ee.yummy.serviceImpl.CanteenServiceImpl;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
import com.j2ee.yummy.yummyEnum.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

import static com.j2ee.yummy.StaticFinalVariable.CANTEEN_PROFIT_PERCENT;

/**
 * @program: yummy
 * @description: 餐厅的 controller类
 * @author: Liu Hanyi
 * @create: 2019-02-07 21:00
 **/

@Controller
public class CanteenController {
    private static final Logger log = LoggerFactory.getLogger(CanteenController.class);


    @Autowired
    CanteenServiceImpl canteenService;
    @Autowired
    BalanceServiceImpl balanceService;
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping(value = "/canteenRegister")
    public String initRegister() {
        return "canteenRegister.html";
    }

    @GetMapping(value = "/canteenLogin")
    public String initLogin() {
        return "canteenLogin.html";
    }

    @GetMapping(value = "/canteenInfo")
    public String initInfo() {
        return "canteenInfo.html";
    }

    @GetMapping(value = "/canteenDisplay")
    public String initDisplay(){
        return "canteenDisplay.html";
    }

    @GetMapping("/canteenBalance")
    public String initBalance() {
        return "canteenBalance.html";
    }

    @PostMapping(value = "/canteen/register")
    @ResponseBody
    public Object register(@RequestBody JSONObject jsonObject) {
        System.out.println("进入 canteenRegister......");

        String canteenName = jsonObject.getString("canteenName");
        String password = jsonObject.getString("password");
        String landlordName = jsonObject.getString("landlordName");
        String phone = jsonObject.getString("phone");
        String categories = jsonObject.getString("categories");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String district = jsonObject.getString("district");
        String detail = jsonObject.getString("detail");

        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetail(detail);

        Canteen canteen = new Canteen();
        canteen.setPassword(password);
        canteen.setCanteenName(canteenName);
        canteen.setLandlordName(landlordName);
        canteen.setPhone(phone);
        canteen.setAddress(address);
        canteen.setCategories(categories);

        canteen = canteenService.register(canteen);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "申请成功");
        map.put("canteenID", canteen.getAccount());
        return map;
    }

    @PostMapping(value = "/canteen/login")
    @ResponseBody
    public Object login(@RequestBody JSONObject jsonObject, HttpSession session) {
        System.out.println("进入 canteenLogin....................");

        long account = jsonObject.getLong("account");
        String password = jsonObject.getString("password");
        System.out.println(account+"!"+password);
        Map<String, Object> map = new HashMap<>();
        try {
            Canteen canteen = canteenService.login(account, password);
            session.setAttribute("canteenID", canteen.getId());
            map.put("success", true);
            map.put("message", "登录成功");
        }catch (NullPointerException e){
            log.info("时间：" + LocalDateTime.now()+", 餐厅帐号不存在", LocalDateTime.now());
            map.put("success", false);
            map.put("message", "帐号/密码失败");
        }



        return map;
    }

    @PostMapping(value = "/canteen/display")
    @ResponseBody
    public List<Canteen> display(){
        System.out.println("进入　CanteenController display.............");

        List<Canteen> canteens = canteenService.getAll();

        return canteens;
    }

    @PostMapping(value = "/canteen/info/get")
    @ResponseBody
    public Canteen get(HttpSession httpSession) {
        System.out.println("进入 canteenGetInfo................");

        long id = (long) httpSession.getAttribute("canteenID");
        Canteen canteen = canteenService.getCanteenByID(id);


        return canteen;
    }

    @PostMapping(value = "/canteen/info/modify")
    @ResponseBody
    public String modify(@RequestBody String json, HttpSession session) {
        System.out.println("进入 canteenModifyInfo................");

        JSONObject jsonObject = JSON.parseObject(json);
        long canteenID = (long) session.getAttribute("canteenID");
        String canteenName = (String) jsonObject.get("name");
        String landlordName = (String) jsonObject.get("landlordName");
        String phone = (String) jsonObject.get("phone");
        String category = jsonObject.getString("category");

        //地址
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String district = jsonObject.getString("district");

        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);

        UnauditedCanInfo unauditedCanInfo = new UnauditedCanInfo();
        unauditedCanInfo.setCanteenID(canteenID);
        unauditedCanInfo.setCanteenName(canteenName);
        unauditedCanInfo.setLandlordName(landlordName);
        unauditedCanInfo.setPhone(phone);
        unauditedCanInfo.setCategories(category);
        unauditedCanInfo.setAddress(address);

        System.out.println(unauditedCanInfo);

        //观察者模式，提供给经理审批
        return canteenService.modify(unauditedCanInfo) ? "success" : "fail";
    }

    @PostMapping(value = "/canteen/balance/get")
    @ResponseBody
    public Object getBalance(HttpSession session) {
        System.out.println("进入 CanteenController getBalance..........");

        long canteenID = (long) session.getAttribute("canteenID");

        Balance balance = balanceService.getBalance(canteenID, UserType.Canteen);
        List<Order> orders = orderService.getOrdersByCanID(canteenID);
        double totalProfit = orders.stream().filter(order -> order.getOrderState().equals(OrderState.完成)).mapToDouble(Order::getTotalPrice).sum();
        int totalOrderNums = (int) orders.stream().filter(order -> order.getOrderState().equals(OrderState.完成)).count();


        Map<String, Object> map = new HashMap<>();
        map.put("message", "获取成功");
        map.put("balance", balance);
        map.put("totalProfit",totalProfit * CANTEEN_PROFIT_PERCENT);
        map.put("totalNums",totalOrderNums);
        return map;
    }
}
