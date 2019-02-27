package com.j2ee.yummy.model.canteen;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: yummy
 * @description: 组合类
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:16
 **/
@Getter
@Setter
public class Combo {
    private long id;
    private long menuID;
    private String name;
    private double price;
    private List<String> dishName;
    private List<Integer> dishRemnants;
    private String profile;
    private int remnants;

    public Combo() {
    }
}
