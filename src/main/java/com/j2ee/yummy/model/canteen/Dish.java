package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.converter.EnumConverter;
import com.j2ee.yummy.yummyEnum.DishCategory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

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
    private Menu menu;

    public Dish() {
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
                ", menu=" + menu.getId() +
                '}';
    }
}
