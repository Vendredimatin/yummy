package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.service.CanteenService;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
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
 * @description: 餐厅的 controller类
 * @author: Liu Hanyi
 * @create: 2019-02-07 21:00
 **/

@Controller
public class CanteenController {
    @Autowired
    CanteenService canteenService;

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

        Address address = new Address();
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);

        Canteen canteen = new Canteen();
        canteen.setPassword(password);
        canteen.setCanteenName(canteenName);
        canteen.setLandlordName(landlordName);
        canteen.setPhone(phone);
        canteen.setAddress(address);

        CanteenCategory canteenCategory = CanteenCategory.valueOf(categories);
        List<CanteenCategory> canteenCategories = new ArrayList<>();
        canteenCategories.add(canteenCategory);

        canteen.setCategories(canteenCategories);

        canteen = canteenService.register(canteen);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "申请成功");
        map.put("canteenID", canteen.getId());
        return map;
    }

    @PostMapping(value = "/canteen/login")
    @ResponseBody
    public Object login(@RequestBody JSONObject jsonObject, HttpSession session) {
        System.out.println("进入 canteenLogin....................");

        long id = jsonObject.getLong("id");
        String password = jsonObject.getString("password");

        Canteen canteen = canteenService.login(id, password);
        session.setAttribute("canteenID", canteen.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "登录成功");
        return map;
    }

    @PostMapping(value = "/canteen/info/get")
    @ResponseBody
    public Canteen get(HttpSession httpSession) {
        System.out.println("进入 canteenGetInfo................");

        long id = (long) httpSession.getAttribute("canteenID");
        Canteen canteen = canteenService.getCanteenByID(id);


        return canteen;
    }

    @PostMapping(value = "/canteen/modify")
    @ResponseBody
    public String modify(@RequestBody String json, HttpSession session) {
        System.out.println("进入 canteenModifyInfo................");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = (long) session.getAttribute("canteenID");
        String password = (String) jsonObject.get("password");
        String canteenName = (String) jsonObject.get("canteenName");
        String landlordName = (String) jsonObject.get("landlordName");
        String phone = (String) jsonObject.get("phone");
        //地址
        Address address = (Address) jsonObject.get("address");

        UnauditedCanInfo unauditedCanInfo = new UnauditedCanInfo();
        unauditedCanInfo.setId(id);
        unauditedCanInfo.setPassword(password);
        unauditedCanInfo.setCanteenName(canteenName);
        unauditedCanInfo.setLandlordName(landlordName);
        unauditedCanInfo.setPhone(phone);
        unauditedCanInfo.setAddress(address);

        //观察者模式，提供给经理审批
        return canteenService.modify(unauditedCanInfo) ? "success" : "fail";
    }
}
