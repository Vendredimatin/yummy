package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.converter.ListConverter2;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * @program: yummy
 * @description: 组合类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:16
 **/
@Getter
@Setter
@Entity
@Table(name = "combo")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255,nullable = false)
    private String name;

    @Column(scale = 2,nullable = false)
    private double price;

    @Column(length = 1000,nullable = false)
    @Convert(converter = ListConverter2.class)
    private List<String> dishNames;

    @Column(length = 1000,nullable = false)
    @Convert(converter = ListConverter2.class)
    private List<Integer> dishRemnants;

    @Column(nullable = false)
    private int remnants;

    @Column(length = 1000,nullable = false)
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "menuID")
    private Menu menu;

    public Combo() {
    }

    @Override
    public String toString() {
        return "Combo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", dishNames=" + dishNames +
                ", dishRemnants=" + dishRemnants +
                ", remnants=" + remnants +
                ", menu=" + menu.getId() +
                '}';
    }
}
