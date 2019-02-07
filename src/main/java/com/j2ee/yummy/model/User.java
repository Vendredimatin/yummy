package com.j2ee.yummy.model;



import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
@MappedSuperclass
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String password;
    protected String name = "";

    public User() {
    }

    public User(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public User(long id,String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
