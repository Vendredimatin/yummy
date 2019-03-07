package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: yummy
 * @description: manager的controller类
 * @author: Liu Hanyi
 * @create: 2019-02-08 08:59
 **/

@Controller
public class ManagerController {
    @Autowired
    ManagerServiceImpl managerService;

    @GetMapping(value = "/managerLogin")
    public String init(){
        return "managerLogin.html";
    }

    @GetMapping(value = "/managerAudit")
    public String initInfo(){
        return "managerAudit.html";
    }

    @GetMapping(value = "/managerStatistics")
    public String initStatistics() {
        return "managerStatistics.html";
    }

    @PostMapping(value = "/manager/login")
    @ResponseBody
    public Object login(@RequestBody JSONObject jsonObject, HttpSession httpSession){
        System.out.println("进入 manger login....................");

        long id =  jsonObject.getLong("account");
        String password = jsonObject.getString("password");
        Manager manager = managerService.login(id,password);

        httpSession.setAttribute("managerID",manager.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "登录成功");
        return map;
    }

    @PostMapping(value = "/manager/unauditedInfo/list")
    @ResponseBody
    public List<UnauditedCanInfo> unauditedCanInfoList(){
        System.out.println("进入 manger unauditedInfo list....................");

        List<UnauditedCanInfo> unauditedCanInfos = managerService.getAllUnaudited();

        return unauditedCanInfos;
    }

    @PostMapping(value = "/manager/pass")
    @ResponseBody
    public Object pass(@RequestBody JSONObject jsonObject){
        System.out.println("进入 manger pass....................");

        long canteenID =  jsonObject.getLong("canteenID");
        managerService.pass(canteenID);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "成功");
        return map;
    }

    @PostMapping(value = "/manager/statistics")
    @ResponseBody
    public Object statistics(){
        System.out.println("进入 manger statistics....................");

        Map<String,Object> map = managerService.getStatistics();
        map.put("message", "成功");

        return map;
    }
}
