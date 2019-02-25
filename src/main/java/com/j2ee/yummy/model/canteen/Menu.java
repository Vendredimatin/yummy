package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.yummyEnum.DishCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @program: yummy
 * @description: 餐厅的菜单类
 * @author: Liu Hanyi
 * @create: 2019-02-04 09:53
 **/
@Getter
@Setter
public class Menu {
    private long id;
    private long canteenID;
    private LocalDate time;
    private Map<DishCategory, List<Dish>> dishes;
    private List<Combo> combos;
    private Preference preference;

    public Menu() {
    }
}
