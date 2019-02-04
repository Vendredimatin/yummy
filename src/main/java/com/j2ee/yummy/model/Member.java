package com.j2ee.yummy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Member extends User{
    private String email;
    private String phone;
    private int memberLevel;
    private List<Address> addresses;
    private String profile;

    public Member() {
    }

    public Member(long id, String password, String name, String email, String phone, int memberLevel, List<Address> addresses, String profile) {
        super(id, password, name);
        this.email = email;
        this.phone = phone;
        this.memberLevel = memberLevel;
        this.addresses = addresses;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Member{" + super.toString() + '\'' +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", memberLevel=" + memberLevel +
                ", addresses=" + addresses +
                ", profile='" + profile + '\'' +
                '}';
    }
}
