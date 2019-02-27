package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.yummyEnum.DishCategory;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: yummy
 * @description: dish
 * @author: Liu Hanyi
 * @create: 2019-02-23 23:16
 **/

@Getter
@Setter
public class Dish {
    private long id;
    private long menuID;
    private DishCategory dishCategory;
    private String name;
    private double price;
    private String description;
    private int remnants;

    public Dish() {
    }
}
