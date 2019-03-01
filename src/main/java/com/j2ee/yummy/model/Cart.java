package com.j2ee.yummy.model;

import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @program: yummy
 * @description: 购物车类
 * @author: Liu Hanyi
 * @create: 2019-02-28 21:50
 **/

@Getter
@Setter
public class Cart {

    private long memberID;
    private long canteenID;
    private List<Dish> dishes = new ArrayList<>();
    private List<Combo> combos = new ArrayList<>();
    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "memberID=" + memberID +
                ", canteenID=" + canteenID +
                ", dishMap=" + dishes.toString() +
                ", comboMap=" + combos.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return canteenID == cart.canteenID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(canteenID);
    }
}
