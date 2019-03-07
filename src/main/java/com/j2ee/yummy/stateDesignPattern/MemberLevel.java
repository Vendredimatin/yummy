package com.j2ee.yummy.stateDesignPattern;

import com.j2ee.yummy.yummyEnum.MemberGrade;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: yummy
 * @description: 会员等级的状态类
 * @author: Liu Hanyi
 * @create: 2019-03-06 19:27
 **/
@Getter
@Setter
public abstract class MemberLevel {
    double maxPrice;
    MemberGrade memberGrade;
    double discount;

    //订单确定完成之后调用
    public abstract MemberGrade checkState(double totalPrice,double cost);

    //用户支付订单的时候就要调用
    public abstract double pay(double totalPrice);

    @Override
    public String toString() {
        return "MemberLevel{" +
                "maxPrice=" + maxPrice +
                ", memberGrade=" + memberGrade +
                ", discount=" + discount +
                '}';
    }
}
