package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.converter.EntityConverter;
import com.j2ee.yummy.model.converter.ListConverter;
import com.j2ee.yummy.observer.Observer;
import com.j2ee.yummy.observer.Subject;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

/**
 * @program: yummy
 * @description: 餐厅类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:53
 **/

@Getter
@Setter
@Entity
@Proxy(lazy = false)
@Table(name = "canteen")
public class Canteen{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected long account;
    protected String password;
    protected String canteenName;
    protected String landlordName;
    protected String phone;
    @Convert(converter = EntityConverter.class)
    protected Address address;
    private String profile = "";
    private String categories;


    public Canteen() {
    }

    public Canteen(long id, String password, String canteenName, String landlordName, String phone, Address address, String profile,String canteenCategories) {
        this.id = id;
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
        this.profile = profile;
        this.categories = canteenCategories;

    }

    public Canteen(String password, String canteenName, String landlordName, String phone, Address address, String profile,String canteenCategories) {
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
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
                ", profile='" + profile + '\'' +
                ", categories=" + categories.toString() +
                '}';
    }

}

