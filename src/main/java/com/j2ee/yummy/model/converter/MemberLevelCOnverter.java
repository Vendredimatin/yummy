package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.stateDesignPattern.MemberLevel;

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
        return JSONObject.parseObject(s,MemberLevel.class);
    }
}
