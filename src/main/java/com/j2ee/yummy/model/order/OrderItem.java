package com.j2ee.yummy.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j2ee.yummy.model.converter.EnumConverter;
import com.j2ee.yummy.yummyEnum.ItemCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @program: yummy
 * @description: 订单表项类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:51
 **/
@Getter
@Setter
@Table(name = "orderItem")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long itemID;
    @Column(nullable = false)
    private String name;
    @Convert(converter = EnumConverter.class)
    @Column(nullable = false)
    private ItemCategory itemCategory;
    @Column(nullable = false)
    private int num;
    @Column(scale = 2,nullable = false)
    private double price;
    @Column(scale = 2,nullable = false)
    private double subtotal;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "orderID")
    @JsonIgnoreProperties("orderItems")
    private Order order;

    public OrderItem() {
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemID=" + itemID +
                ", name='" + name + '\'' +
                ", itemCategory=" + itemCategory +
                ", num=" + num +
                ", price=" + price +
                ", subtotal=" + subtotal +
               /* ", order=" + order +*/
                '}';
    }
}
