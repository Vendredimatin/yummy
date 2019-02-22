package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: yummy
 * @description: manager的controller类
 * @author: Liu Hanyi
 * @create: 2019-02-08 08:59
 **/

@Controller
public class ManagerController {

    @PostMapping(value = "/manager/login")
    @ResponseBody
    public JSONObject login(@RequestBody String json){
        System.out.println("进入 manger login....................");

        JSONObject jsonObject = JSON.parseObject(json);
        long id = (long) jsonObject.get("id");
        String password = (String) jsonObject.get("password");

    }
}
