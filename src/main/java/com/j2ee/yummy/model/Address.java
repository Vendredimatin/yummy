package com.j2ee.yummy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: yummy
 * @description: 用户的送货地址
 * @author: Liu Hanyi
 * @create: 2019-02-04 09:46
 **/
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    private long id;
    private long memberID;
    private String name;
    private String phone;
    private String city;
    private String province;
    private String district;
    private String detail;

    public Address() {
    }

    public Address(long id, long memberID, String name, String phone, String city, String province, String district, String detail) {
        this.id = id;
        this.memberID = memberID;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.province = province;
        this.district = district;
        this.detail = detail;
    }

    public Address(long memberID, String name, String phone, String city, String province, String district, String detail) {
        this.memberID = memberID;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.province = province;
        this.district = district;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", memberID=" + memberID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
