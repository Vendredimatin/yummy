package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.stateDesignPattern.DiamondLevel;
import com.j2ee.yummy.stateDesignPattern.GoldLevel;
import com.j2ee.yummy.stateDesignPattern.MemberLevel;
import com.j2ee.yummy.stateDesignPattern.OrdinaryLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @program: yummy
 * @description:
 * @author: Liu Hanyi
 * @create: 2019-03-06 19:35
 **/
@Converter(autoApply = true)
public class MemberLevelCOnverter implements AttributeConverter<MemberLevel,String> {
    @Override
    public String convertToDatabaseColumn(MemberLevel memberLevel) {
        return JSON.toJSONString(memberLevel);
    }

    @Override
    public MemberLevel convertToEntityAttribute(String s) {
        System.out.println(s);
        MemberLevel memberLevel;
        if (s.indexOf("普通会员") > -1)
            memberLevel = JSONObject.parseObject(s,OrdinaryLevel.class);
        else if (s.indexOf("黄金会员") > -1)
            memberLevel = JSONObject.parseObject(s, GoldLevel.class);
        else memberLevel = JSONObject.parseObject(s, DiamondLevel.class);
        return memberLevel;
    }
}
