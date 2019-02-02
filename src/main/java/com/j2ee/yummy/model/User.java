package com.j2ee.yummy.model;



import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Proxy(lazy = false)
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    int id;
    String account;
    double balance;
    String password;

    public User() {
    }

    public User(String account, double balance, String password) {
        this.account = account;
        this.balance = balance;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                '}';
    }
}
