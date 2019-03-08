package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.service.AddressService;
import com.j2ee.yummy.serviceImpl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: yummy
 * @description: address的Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-07 19:28
 **/

@Controller
public class AddressController {
    @Autowired
    AddressServiceImpl addressService;

    @GetMapping(value = "/memberAddress")
    public String init(){
        return "address.html";
    }

    @PostMapping(value = "/member/address/get")
    @ResponseBody
    public JSONObject getAddress(HttpSession session){
        System.out.println("进入 getAddress ...........");

        long memberID = (long) session.getAttribute("memberID");

        List<Address> addresses = addressService.getAddressesByMemberID(memberID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("addresses",addresses);

        return jsonObject;
    }

    @PostMapping(value = "/member/address/add")
    @ResponseBody
    public Object addAddress(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 addAddress ...........");

        long memberID = (long) session.getAttribute("memberID");
        String name = jsonObject.getString("name");
        String phone = jsonObject.getString("phone");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String district = jsonObject.getString("district");
        String detail = jsonObject.getString("detail");

        Address address = new Address(memberID,name,phone,city,province,district,detail);
        long addressID = addressService.add(address).getId();

        System.out.println(addressID);

        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message","新建成功了！");
        map.put("addressID",addressID);
        return map;

    }

    @PostMapping(value = "/member/address/modify")
    @ResponseBody
    public Object modifyAddress(@RequestBody JSONObject jsonObject){
        System.out.println("进入 modifyAddress ...........");

        long id = Long.parseLong(jsonObject.getString("addressID"));
        String name = jsonObject.getString("name");
        String phone = jsonObject.getString("phone");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String district = jsonObject.getString("district");
        String detail = jsonObject.getString("detail");

        Address address = addressService.getAddressByID(id);
        address.setName(name);
        address.setPhone(phone);
        address.setCity(city);
        address.setProvince(province);
        address.setDistrict(district);
        address.setDetail(detail);

        addressService.update(address);
        System.out.println(address);

        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message","修改成功了！");
        return map;
    }

    @PostMapping(value = "/member/address/delete")
    @ResponseBody
    public Object deleteAddress(@RequestBody JSONObject jsonObject){
        System.out.println("进入 deleteAddress ...........");
        long id = Long.parseLong(jsonObject.getString("id"));

        addressService.delete(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message","删除成功了！");
        return map;
    }





}
