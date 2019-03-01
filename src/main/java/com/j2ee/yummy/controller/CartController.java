package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Cart;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @program: yummy
 * @description: 购物车的controller
 * @author: Liu Hanyi
 * @create: 2019-02-28 21:48
 **/

@Controller
public class CartController {
    @Autowired
    AddressService addressService;

    @PostMapping(value = "/member/cart/add")
    @ResponseBody
    public Object add(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入　CartController add.................");

        String kind = jsonObject.getString("kind");
        long id = jsonObject.getLong("id");
        double price = jsonObject.getDouble("price");
        String name = jsonObject.getString("name");

        Set<Cart> carts;
        //当会员重新进入另外一家店时，应该声明另外一个cart
        if (Objects.isNull(session.getAttribute("carts"))){
            carts = new HashSet<>();
        }else carts = (Set<Cart>) session.getAttribute("carts");

        long scanCanteenID = (Long) session.getAttribute("scanCanteenID");

        Cart cart = null;
        for (Cart tmp : carts) {
            if (tmp.getCanteenID() == scanCanteenID)
                cart = tmp;
        }

        if (cart == null){
            cart = new Cart();
            cart.setCanteenID(scanCanteenID);
            cart.setMemberID((Long) session.getAttribute("memberID"));
        }

        try{
            if (kind.equals("combo")){
                List<Combo> combos = cart.getCombos();
                Combo combo = new Combo();
                combo.setId(id);
                combo.setName(name);
                combo.setPrice(price);
                combos.add(combo);
                cart.setCombos(combos);
            }else {
                List<Dish> dishes = cart.getDishes();
                Dish dish = new Dish();
                dish.setId(id);
                dish.setName(name);
                dish.setPrice(price);
                dishes.add(dish);
                cart.setDishes(dishes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        carts.add(cart);

        session.setAttribute("carts",carts);

        Map<String,Object> map = new HashMap<>();
        map.put("message","添加成功");
        return map;
    }

    @PostMapping(value = "/member/cart/display")
    @ResponseBody
    public Object cartDisplay(HttpSession session){
        System.out.println("进入 CartController cartDisplay.............");

        long scanCanteenID = (long) session.getAttribute("scanCanteenID");

        Set<Cart> carts = (Set<Cart>) session.getAttribute("carts");

        Cart cart = null;
        for (Cart tmp: carts) {
            if (tmp.getCanteenID() == scanCanteenID)
                cart = tmp;
        }

        List<Address> addresses = addressService.getAddressesByMemberID((Long) session.getAttribute("memberID"));
        Map<String,Object> map = new HashMap<>();
        map.put("cart",cart);
        map.put("addresses",addresses);

        return map;
    }
}
