package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @GetMapping(value = "/")
    public String init(){
        return "a.html";
    }

    @PostMapping(value = "/hello")
    @ResponseBody
    public String hello(@RequestBody String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("account"));
        System.out.println(jsonObject.get("password"));
        return "success";
    }
}
