package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.service.CancelledMemberService;
import com.j2ee.yummy.service.MemberService;
import com.j2ee.yummy.serviceImpl.CancelledMemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: yummy
 * @description: 会员Controller类
 * @author: Liu Hanyi
 * @create: 2019-02-04 16:49
 **/
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(CanteenController.class);

    @Autowired
    MemberService memberService;
    @Autowired
    CancelledMemberServiceImpl cancelledMemberService;

    @GetMapping(value = "/login")
    public String init() {
        return "login.html";
    }


    @PostMapping(value = "/member/login")
    @ResponseBody
    public Object login(@RequestBody JSONObject jsonObject, HttpSession session) {

        String email = jsonObject.getString("email");
        String password =  jsonObject.getString("password");

        System.out.println(email+"@"+password);


        Map<String, Object> map = new HashMap<>();
        //已注销的用户无法再登录，但是保留数据库中的数据
        if (cancelledMemberService.isCancelled(email)){
            map.put("success", false);
            map.put("message", "该帐号已被注销");
            return map;
        }


        try {
            Member member = memberService.login(email, password);
            session.setAttribute("memberID", member.getId());
            map.put("success", true);
            map.put("message", "登录成功");
        } catch (NullPointerException e) {
            log.info("时间：" + LocalDateTime.now() + ", 会员帐号不存在", LocalDateTime.now());
            map.put("success", false);
            map.put("message", "帐号/密码失败");
        }

        return map;
    }

    @PostMapping(value = "/member/logoff")
    @ResponseBody
    public Object logoff(HttpSession session){
        long memberID = (long) session.getAttribute("memberID");

        Member member = memberService.getMemberByID(memberID);
        cancelledMemberService.add(member);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "注销成功");
        return map;
    }
}
