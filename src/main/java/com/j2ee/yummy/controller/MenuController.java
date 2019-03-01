package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.model.canteen.Menu;
import com.j2ee.yummy.model.canteen.Preference;
import com.j2ee.yummy.serviceImpl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @program: yummy
 * @description: menu的控制器类
 * @author: Liu Hanyi
 * @create: 2019-02-27 16:46
 **/

@Controller
public class MenuController {
    @Autowired
    MenuServiceImpl menuService;

    @GetMapping(value = "/canteenMenuCreate")
    public String init(){
        return "canteenMenuCreate.html";
    }

    @GetMapping(value = "/canteenMenuDisplay")
    public String initDisplay() { return "canteenMenuDisplay.html";}

    @PostMapping(value = "/canteen/menu/create")
    @ResponseBody
    public Object createMenu(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入 MenuControoler createMenu..........");

        long canteenID = (long) session.getAttribute("canteenID");

        JSONArray jsonArray = jsonObject.getJSONArray("dishes");
        List<Dish> dishes = jsonArray.toJavaList(Dish.class);

        List<Combo> combos = jsonObject.getJSONArray("combos").toJavaList(Combo.class);
        Preference preference = jsonObject.getObject("preference",Preference.class);

        Menu menu = new Menu();
        menu.setCanteenID(canteenID);

        menu.setTime(LocalDate.now());
        menu.setPreference(preference);

        for (int i = 0; i < combos.size(); i++) {
            combos.get(i).setMenu(menu);
        }

        for (int i = 0; i < dishes.size(); i++) {
            dishes.get(i).setMenu(menu);
        }

        menu.setCombos(new HashSet<>(combos));
        menu.setDishes(new HashSet<>(dishes));

        System.out.println(dishes);
        System.out.println(combos);
        System.out.println(preference);
        menuService.save(menu);

        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message","新建成功了！");
        return map;
    }

    @PostMapping(value = "/canteen/menu/display")
    @ResponseBody
    public Object displayMenu(HttpSession session){
        System.out.println("进入 MenuController displayMenu...............");

        long canteenID = (long) session.getAttribute("scanCanteenID");
        List<Menu> menus = menuService.getMenusByCanteenID(canteenID);

        Map<String,Object> map = new HashMap<>();
        map.put("menus",menus);
        return map;
    }
}
