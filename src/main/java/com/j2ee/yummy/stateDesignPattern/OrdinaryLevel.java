package com.j2ee.yummy.stateDesignPattern;

import com.j2ee.yummy.yummyEnum.MemberGrade;

/**
 * @program: yummy
 * @description: 普通会员
 * @author: Liu Hanyi
 * @create: 2019-03-06 19:49
 **/

public class OrdinaryLevel extends MemberLevel{
    private static final double MAX_PRICE = 100;
    private static final double DISCOUNT = 1.0;

    public OrdinaryLevel() {
        memberGrade = MemberGrade.普通会员;
        maxPrice = MAX_PRICE;
        discount = DISCOUNT;
    }


    @Override
    public MemberGrade checkState(double totalPrice, double cost) {
        if (totalPrice + cost > MAX_PRICE)
            return MemberGrade.黄金会员;
        return memberGrade;
    }

    @Override
    public double pay(double totalPrice) {
        return totalPrice;
    }
}
