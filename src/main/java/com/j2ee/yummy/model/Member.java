package com.j2ee.yummy.model;

import com.j2ee.yummy.model.converter.ListConverter2;
import com.j2ee.yummy.model.converter.MemberLevelCOnverter;
import com.j2ee.yummy.stateDesignPattern.MemberLevel;
import com.j2ee.yummy.stateDesignPattern.OrdinaryLevel;
import com.j2ee.yummy.yummyEnum.MemberGrade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table(name = "member")
@Entity
public class Member extends User {
    private String email;
    private String phone;
    @Convert(converter = MemberLevelCOnverter.class)
    private MemberLevel memberLevel;
    @Convert(converter = ListConverter2.class)
    private List<Address> addresses;
    private String profile;

    public Member() {
        memberLevel = new OrdinaryLevel();
    }

    public Member(long id, String password, String name, String email, String phone, MemberLevel memberLevel, List<Address> addresses, String profile) {
        super(id, password, name);
        this.email = email;
        this.phone = phone;
        this.memberLevel = memberLevel;
        this.addresses = addresses;
        this.profile = profile;
    }

    public double pay(double totalPrice) {
        return memberLevel.pay(totalPrice);
    }

    public MemberGrade checkLevel(double totalPrice, double cost) {
        return memberLevel.checkState(totalPrice,cost);
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

    public void setMemberGrade(MemberGrade memberGrade) {
        memberLevel.setMemberGrade(memberGrade);
    }
}
