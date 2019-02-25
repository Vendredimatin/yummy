package com.j2ee.yummy.model.canteen;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: yummy
 * @description: 优惠
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:17
 **/
@Getter
@Setter
public class Preference {

    private long id;
    private long menuID;
    //可以使用表驱动
    private List<Double> targetSums;
    private List<Double> discountSums;

    public Preference() {
    }
}
