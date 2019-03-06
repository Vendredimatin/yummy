package com.j2ee.yummy.stateDesignPattern;

import com.j2ee.yummy.yummyEnum.MemberGrade;

/**
 * @program: yummy
 * @description: 黄金会员
 * @author: Liu Hanyi
 * @create: 2019-03-06 19:49
 **/

public class GoldLevel extends MemberLevel{
    private static final double MAX_PRICE = 500;
    private static final double DISCOUNT = 0.9;

    public GoldLevel() {
        memberGrade = MemberGrade.黄金会员;
        maxPrice = MAX_PRICE;
        discount = DISCOUNT;
    }


    //当黄金会员消费大于５００，则直接升为钻石会员
    @Override
    public MemberGrade checkState(double totalPrice, double cost) {
        if (cost + totalPrice > MAX_PRICE)
            return MemberGrade.钻石会员;
        return memberGrade;
    }

    @Override
    public double pay(double totalPrice) {
        return totalPrice * discount;
    }
}
