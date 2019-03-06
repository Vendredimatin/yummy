package com.j2ee.yummy.stateDesignPattern;

import com.j2ee.yummy.yummyEnum.MemberGrade;

/**
 * @program: yummy
 * @description: 钻石会员
 * @author: Liu Hanyi
 * @create: 2019-03-06 19:50
 **/

public class DiamondLevel extends MemberLevel{
    private static final double MAX_PRICE = Double.POSITIVE_INFINITY;
    private static final double DISCOUNT = 0.8;

    public DiamondLevel() {
        memberGrade = MemberGrade.钻石会员;
        maxPrice = MAX_PRICE;
        discount = DISCOUNT;
    }


    @Override
    public MemberGrade checkState(double totalPrice, double cost) {
        return memberGrade;
    }

    @Override
    public double pay(double totalPrice) {
        return totalPrice * discount;
    }
}
