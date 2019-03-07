package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.serviceImpl.CancelledMemberServiceImpl;
import com.j2ee.yummy.serviceImpl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: yummy
 * @description: 注册Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-04 17:20
 **/
@Controller
public class RegisterController {
    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    CancelledMemberServiceImpl cancelledMemberService;

    @GetMapping(value = "/memberRegister")
    public String init(){
        return "register.html";
    }



    @PostMapping(value = "/member/register")
    @ResponseBody
    public Object register(@RequestBody JSONObject jsonObject){
        String name = jsonObject.getString("name");
        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");
        String phone = jsonObject.getString("phone");

        System.out.println(email);
        System.out.println(password);

        memberService.register(email,password,name,phone);
        Map<String,Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "注册成功");
        return map;
    }

    @PostMapping(value = "/member/register/identifyCode")
    @ResponseBody
    public Object identifyCode(@RequestBody JSONObject jsonObject){

        String email = jsonObject.getString("email");

        Map<String,Object> map = new HashMap<>();

        if (memberService.isExists(email)){
            map.put("success", false);
            map.put("message", "邮箱已被注册，请重新填写邮箱");
        }else {
            String identifyCode = memberService.sendEmail(email);
            map.put("success", true);
            map.put("message", "发送成功");
            map.put("identifyCode",identifyCode);
        }


        return map;
    }
}
