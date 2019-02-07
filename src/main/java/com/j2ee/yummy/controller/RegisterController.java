package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: yummy
 * @description: 注册Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-04 17:20
 **/
@Controller
public class RegisterController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/register")
    public String init(){
        return "register.html";
    }



    @PostMapping(value = "/register")
    @ResponseBody
    public String register(@RequestBody String json){
        JSONObject jsonObject = JSONObject.parseObject(json);

        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");

        System.out.println(email);
        System.out.println(password);
        return memberService.register(email,password);
    }
}
