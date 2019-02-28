package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.converter.ListConverter2;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * @program: yummy
 * @description: 优惠
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:17
 **/
@Getter
@Setter
@Entity
@Table(name = "preference")
@Proxy(lazy = false)
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //可以使用表驱动
    @Convert(converter = ListConverter2.class)
    private List<Double> targetSums;

    @Convert(converter = ListConverter2.class)
    private List<Double> discountSums;

    public Preference() {
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", targetSums=" + targetSums +
                ", discountSums=" + discountSums +
                '}';
    }
}
