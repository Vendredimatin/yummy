package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: yummy
 * @description: 餐厅类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:53
 **/
@Getter
@Setter
public class Canteen {
    private long id;
    private String password;
    private String canteenName;
    private String landlordName;
    private String phone;
    private Address address;
    private long menuID;
    private String profile;
    private List<CanteenCategory> categories;

    public Canteen() {
    }

    public Canteen(long id, String password, String canteenName, String landlordName, String phone, Address address, long menuID, String profile,List<CanteenCategory> canteenCategories) {
        this.id = id;
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
        this.menuID = menuID;
        this.profile = profile;
        this.categories = canteenCategories;
    }

    public Canteen(String password, String canteenName, String landlordName, String phone, Address address, long menuID, String profile,List<CanteenCategory> canteenCategories) {
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
        this.menuID = menuID;
        this.profile = profile;
        this.categories = canteenCategories;
    }

    @Override
    public String toString() {
        return "Canteen{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", canteenName='" + canteenName + '\'' +
                ", landlordName='" + landlordName + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", menuID=" + menuID +
                ", profile='" + profile + '\'' +
                '}';
    }
}
