package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.yummyEnum.UserType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Entity;

/**
 * @program: yummy
 * @description:
 * @author: Liu Hanyi
 * @create: 2019-03-02 20:00
 **/

@Converter
public class UserTypeConverter implements AttributeConverter<UserType,String> {
    @Override
    public String convertToDatabaseColumn(UserType userType) {
        return JSON.toJSONString(userType);
    }

    @Override
    public UserType convertToEntityAttribute(String s) {
        return JSONObject.parseObject(s,UserType.class);
    }
}
