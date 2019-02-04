package com.j2ee.yummy.model;

import com.j2ee.yummy.yummyEnum.CanteenCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: yummy
 * @description: 餐厅类
 * @author: Liu Hanyi
 * @create: 2019-02-04 09:49
 **/

@Getter
@Setter
public class Canteen {
    private long id;
    private String password;
    private String name;
    private String phone;
    private String profile;
    private Address address;
    private Menu menu;
    private List<CanteenCategory> canteenCategories;

    public Canteen() {
    }

    public Canteen(long id, String password, String name, String phone, String profile, Address address, Menu menu, List<CanteenCategory> canteenCategories) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.profile = profile;
        this.address = address;
        this.menu = menu;
        this.canteenCategories = canteenCategories;
    }

    @Override
    public String toString() {
        return "Canteen{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", profile='" + profile + '\'' +
                ", address=" + address +
                ", menu=" + menu +
                ", canteenCategories=" + canteenCategories.toString() +
                '}';
    }
}
