package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: yummy
 * @description: address的Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-07 19:28
 **/

@Controller
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping(value = "/member/address")
    public String init(){
        return "address.html";
    }

    @PostMapping(value = "/member/address/add")
    @ResponseBody
    public String addAddress(@RequestBody String json, HttpSession session){
        System.out.println("进入 addAddress ...........");

        JSONObject jsonObject = JSON.parseObject(json);

        long memberID = (long) session.getAttribute("memberID");
        String name = jsonObject.getString("name");
        String phone = jsonObject.getString("phone");
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        String district = jsonObject.getString("district");
        String detail = jsonObject.getString("detail");

        Address address = new Address(memberID,name,phone,city,province,district,detail);

        return addressService.add(address)?"success":"fail";

    }

    @PostMapping(value = "/member/address/modify")
    @ResponseBody
    public String modifyAddress(@RequestBody String json){
        System.out.println("进入 modifyAddress ...........");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = Long.parseLong(jsonObject.getString("id"));
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

        return addressService.update(address)?"success":"fail";
    }

    @PostMapping(value = "/member/address/delete")
    @ResponseBody
    public String deleteAddress(@RequestBody String json){
        System.out.println("进入 deleteAddress ...........");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = Long.parseLong(jsonObject.getString("id"));

        return addressService.delete(id)?"success":"fail";
    }





}
