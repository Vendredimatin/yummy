package com.j2ee.yummy.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.converter.EnumConverter;
import com.j2ee.yummy.model.converter.OrderStateConverter;
import com.j2ee.yummy.yummyEnum.OrderState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(nullable = false)
    protected long memberID;
    @Column(nullable = false)
    protected long canteenID;
    @Column(nullable = false)
    protected String memberName;
    @Column(nullable = false)
    protected String memberPhone;
    @Convert(converter = EnumConverter.class)
    @Column(length = 1000,nullable = false)
    protected Address memberAddress;
    @Column(nullable = false)
    protected LocalDateTime time;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", memberID=" + memberID +
                ", canteenID=" + canteenID +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", memberAddress=" + memberAddress +
                ", time=" + time +
                ", totalPrice=" + totalPrice +
                ", orderState=" + orderState +
                ", orderItems=" + orderItems.toString() +
                '}';
    }
}
