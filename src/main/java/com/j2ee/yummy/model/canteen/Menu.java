package com.j2ee.yummy.model.canteen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @program: yummy
 * @description: 餐厅的菜单类
 * @author: Liu Hanyi
 * @create: 2019-02-04 09:53
 **/
@Getter
@Setter
@Entity
@Proxy(lazy = false)
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "canteenID",nullable = false)
    private long canteenID;
    @Column(name = "time",nullable = false)
    private LocalDate time;
    @OneToMany(mappedBy = "menu",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties("menu")
    private Set<Dish> dishes;
    @OneToMany(mappedBy = "menu",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties("menu")
    private Set<Combo> combos;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferenceID",referencedColumnName = "id")
    private Preference preference;

    public Menu() {
    }


    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", canteenID=" + canteenID +
                ", time=" + time +
                ", dishes=" + dishes +
                ", combos=" + combos +
                ", preference=" + preference +
                '}'+'\n';
    }
}
