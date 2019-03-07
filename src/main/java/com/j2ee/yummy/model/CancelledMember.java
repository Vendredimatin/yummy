package com.j2ee.yummy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @program: yummy
 * @description: 已注销的会员类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:09
 **/
@Getter
@Setter
@Entity
@Table(name = "cancelledMember")
public class CancelledMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long memberID;
    private String email;

    public CancelledMember() {
    }

    public CancelledMember(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
