package com.j2ee.yummy.controller;

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

/**
 * @program: yummy
 * @description: 会员类的controller
 * @author: Liu Hanyi
 * @create: 2019-02-07 10:22
 **/

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping("/member/info")
    public String init(){return "memberInfo.html";}


    @PostMapping(value = "/member/info")
    @ResponseBody
    public Member getInfo(HttpSession session){
        System.out.println("进入 getInfo............");

        long userID = (long) session.getAttribute("memberID");
        Member member = memberService.getMemberByID(userID);

        return member;
    }

    @PostMapping(value = "/member/modifyInfo")
    @ResponseBody
    public String modifyInfo(@RequestBody JSONObject jsonObject,HttpSession session){
        System.out.println("进入 modifyInfo............");

        String email = jsonObject.getString("email");
        String name = jsonObject.getString("name");
        String phone = jsonObject.getString("phone");

        long userID = (long) session.getAttribute("memberID");
        Member member = memberService.getMemberByID(userID);

        member.setEmail(email);
        member.setPhone(phone);
        member.setName(name);
        System.out.println(member);

        return memberService.update(member);
    }
}
