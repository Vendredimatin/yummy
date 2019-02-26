package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.model.Address;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @program: yummy
 * @description: 实体转换类
 * @author: Liu Hanyi
 * @create: 2019-02-26 15:36
 **/
@Converter(autoApply = true)
public class EntityConverter implements AttributeConverter<Address,String> {
    @Override
    public String convertToDatabaseColumn(Address address) {
        String s = JSON.toJSONString(address);
        return s;
    }

    @Override
    public Address convertToEntityAttribute(String s) {
        Address address = JSON.parseObject(s,Address.class);
        return address;
    }


}
