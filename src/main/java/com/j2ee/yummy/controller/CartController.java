package com.j2ee.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Cart;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.model.canteen.Menu;
import com.j2ee.yummy.service.AddressService;
import com.j2ee.yummy.service.CanteenService;
import com.j2ee.yummy.serviceImpl.ComboServiceImpl;
import com.j2ee.yummy.serviceImpl.DishServiceImpl;
import com.j2ee.yummy.serviceImpl.MemberServiceImpl;
import com.j2ee.yummy.serviceImpl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    CanteenService canteenService;
    @Autowired
    ComboServiceImpl comboService;
    @Autowired
    DishServiceImpl dishService;
    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    MenuServiceImpl menuService;

    @PostMapping(value = "/member/cart/add")
    @ResponseBody
    public Object add(@RequestBody JSONObject jsonObject, HttpSession session){
        System.out.println("进入　CartController add.................");

        String kind = jsonObject.getString("kind");
        long id = jsonObject.getLong("id");
        double price = jsonObject.getDouble("price");
        String name = jsonObject.getString("name");
        long menuID = jsonObject.getLong("menuID");

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
            cart.setMenuID(menuID);
            cart.setMemberID((Long) session.getAttribute("memberID"));
        }



        try{
            if (kind.equals("combo")){
                Set<Combo> combos = cart.getCombos();
                Combo combo = new Combo();
                combo.setId(id);
                combo.setName(name);
                combo.setPrice(price);
                combos.add(combo);
                cart.setCombos(combos);
            }else {
                Set<Dish> dishes = cart.getDishes();
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
        session.setAttribute("checkoutCanID",scanCanteenID);
        Set<Cart> carts = (Set<Cart>) session.getAttribute("carts");
        Cart cart = null;
        try{
            for (Cart tmp: carts) {
                if (tmp.getCanteenID() == scanCanteenID)
                    cart = tmp;
            }
        }catch (NullPointerException e){
            Map<String,Object> map = new HashMap<>();
            map.put("message","还未加入购物车");
            map.put("success",false);
            return map;
        }


        //添加优惠条件和会员等级
        Menu menu = menuService.getMenuByID(cart.getMenuID());
        cart.setPreference(menu.getPreference());

        Member member = memberService.getMemberByID(cart.getMemberID());
        cart.setMemberLevel(member.getMemberLevel());

        List<Long> dishesID = cart.getDishes().stream().map(Dish::getId).collect(Collectors.toList());
        List<Long> combosID = cart.getCombos().stream().map(Combo::getId).collect(Collectors.toList());

        //为了防止超卖
        List<Dish> dishes = dishService.getDishesByIDs(dishesID);
        List<Combo> combos = comboService.getCombosByIDs(combosID);

        cart.setDishes(new HashSet<>(dishes));
        cart.setCombos(new HashSet<>(combos));

        List<Address> addresses = addressService.getAddressesByMemberID((Long) session.getAttribute("memberID"));

        Canteen canteen = canteenService.getCanteenByID(cart.getCanteenID());
        Address canteenAddress = canteen.getAddress();

        Map<String,Object> map = new HashMap<>();
        map.put("cart",cart);
        map.put("addresses",addresses);
        map.put("canteenAddress",canteenAddress);
        map.put("memberName",session.getAttribute("memberName"));
        return map;
    }
}
