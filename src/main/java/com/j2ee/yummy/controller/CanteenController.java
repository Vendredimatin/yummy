package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.service.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @program: yummy
 * @description: 餐厅的 controller类
 * @author: Liu Hanyi
 * @create: 2019-02-07 21:00
 **/
@Controller
public class CanteenController {
    @Autowired
    CanteenService canteenService;

    @PostMapping(value = "/canteen/register")
    @ResponseBody
    public String register(@RequestBody String json){
        System.out.println("进入 canteenRegister......");

        JSONObject jsonObject = JSON.parseObject(json);
        String password = (String) jsonObject.get("password");

        return canteenService.register(password)?"success":"fail";
    }

    @PostMapping(value = "/canteen/login")
    @ResponseBody
    public String login(@RequestBody String json, HttpSession session){
        System.out.println("进入 canteenLogin....................");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = (long) jsonObject.get("id");
        String password = (String) jsonObject.get("password");

        Canteen canteen = canteenService.login(id,password);
        session.setAttribute("canteenID",canteen.getId());

        return Objects.isNull(canteen)?"fail":"success";
    }

    @PostMapping(value = "/canteen/modify")
    @ResponseBody
    public String modify(@RequestBody String json,HttpSession session){
        System.out.println("进入 canteenModifyInfo................");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = (long) session.getAttribute("canteenID");
        String password = (String) jsonObject.get("password");
        String canteenName = (String) jsonObject.get("canteenName");
        String landlordName = (String) jsonObject.get("landlordName");
        String phone = (String) jsonObject.get("phone");
        //地址
        Address address = (Address) jsonObject.get("address");

        Canteen canteen = new Canteen();
        canteen.setId(id);
        canteen.setPassword(password);
        canteen.setCanteenName(canteenName);
        canteen.setLandlordName(landlordName);
        canteen.setPhone(phone);
        canteen.setAddress(address);

        //观察者模式，提供给经理审批
        return canteenService.modify(canteen)?"success":"fail";
    }
}
