package com.j2ee.yummy.model;

import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Dish;
import com.j2ee.yummy.model.canteen.Preference;
import com.j2ee.yummy.stateDesignPattern.MemberLevel;
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
    private long menuID;
    private Set<Dish> dishes = new HashSet<>();
    private Set<Combo> combos = new HashSet<>();
    private Preference preference;
    private MemberLevel memberLevel;
    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "memberID=" + memberID +
                ", canteenID=" + canteenID +
                ", menuID=" + menuID +
                ", dishes=" + dishes.toString() +
                ", combos=" + combos.toString() +
                ", preference=" + preference +
                ", memberLevel=" + memberLevel +
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
