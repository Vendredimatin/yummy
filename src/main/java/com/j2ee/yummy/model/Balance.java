package com.j2ee.yummy.model;

import com.j2ee.yummy.model.converter.UserTypeConverter;
import com.j2ee.yummy.yummyEnum.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @program: yummy
 * @description: 存储在银行的金额
 * @author: Liu Hanyi
 * @create: 2019-03-02 19:51
 **/
@Getter
@Setter
@Table(name = "balance")
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected long userID;
    protected double balance;
    protected String password;
    @Convert(converter = UserTypeConverter.class)
    protected UserType userType;

    public Balance() {
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", userID=" + userID +
                ", balance=" + balance +
                '}';
    }
}
