package com.j2ee.yummy.PO;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @program: yummy
 * @description: member的PO类
 * @author: Liu Hanyi
 * @create: 2019-02-06 10:10
 **/
@Entity
@Table(name = "member")
@Getter
@Setter
public class MemberPO extends User {
    private String email = "";
    private String phone = "";
    private int memberLevel = 1;
    private String addresses = "";
    private String profile = "";

    public MemberPO() {
    }

    public MemberPO(long id, String password, String name, String email, String phone, int memberLevel, String addresses, String profile) {
        super(id, password, name);
        this.email = email;
        this.phone = phone;
        this.memberLevel = memberLevel;
        this.addresses = addresses;
        this.profile = profile;
    }
}
