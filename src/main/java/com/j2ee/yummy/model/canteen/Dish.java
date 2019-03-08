package com.j2ee.yummy.model.canteen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @program: yummy
 * @description: dish
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:16
 **/

@Getter
@Setter
@Table(name = "dish")
@Entity
/*
@Proxy(lazy = false)
*/
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dishCategory",length = 255, nullable = false)
    private String dishCategory;

    @Column(length = 255,nullable = false)
    private String name;

    @Column(scale = 2,nullable = false)
    private double price;

    @Column(length = 1000,nullable = false)
    private String description;

    @Column(nullable = false)
    private int remnants;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "menuID")
    @JsonIgnoreProperties("dishes")
    private Menu menu;

    public Dish() {
    }

    public Dish(String dishCategory, String name, double price, String description, int remnants, Menu menu) {
        this.dishCategory = dishCategory;
        this.name = name;
        this.price = price;
        this.description = description;
        this.remnants = remnants;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", dishCategory=" + dishCategory +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", remnants=" + remnants +
               /* ", menu=" + menu.getId() +*/
                '}';
    }


}
