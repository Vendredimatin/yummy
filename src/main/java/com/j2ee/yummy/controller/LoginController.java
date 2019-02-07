package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @program: yummy
 * @description: 会员Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-04 16:49
 **/
@Controller
public class LoginController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/login")
    public String init(){
        return "login.html";
    }



    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody String json,HttpSession session){
        JSONObject jsonObject = JSONObject.parseObject(json);

        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");

        Member member = memberService.login(email,password);
        session.setAttribute("userID",member.getId());

        return Objects.isNull(member)?"fail":"success";
    }
}
