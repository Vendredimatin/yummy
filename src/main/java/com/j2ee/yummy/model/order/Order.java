package com.j2ee.yummy.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.converter.EntityConverter;
import com.j2ee.yummy.model.converter.OrderStateConverter;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @program: yummy
 * @description: 订单类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:42
 **/
@Getter
@Setter
@Entity
@Proxy(lazy = false)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(nullable = false)
    protected long memberID;
    @Column(nullable = false)
    protected long canteenID;
    @Column(nullable = false)
    protected String canteenName;
    @Column(nullable = false)
    protected String memberName;
    @Column(nullable = false)
    protected String memberPhone;
    @Column(length = 1000,nullable = false)
    @Convert(converter = EntityConverter.class)
    protected Address memberAddress;
    @Column(nullable = false)
    protected LocalDateTime time;
    @Column
    protected int deliveringTime;
    @Column(scale = 2,nullable = false)
    protected double totalPrice;
    @Column(nullable = false)
    @Convert(converter = OrderStateConverter.class)
    protected OrderState orderState;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties("order")
    protected Set<OrderItem> orderItems;

    public Order() {
    }

    public double unsubscribe(){
        if (orderState.equals(OrderState.派送中))
            return totalPrice*0.8;
        else return 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", memberID=" + memberID +
                ", canteenID=" + canteenID +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", time=" + time +
                ", memberAddress=" + memberAddress +
                ", totalPrice=" + totalPrice +
                ", orderState=" + orderState +
                ", orderItems=" + orderItems.toString() +
                '}'+'\n';
    }


}
