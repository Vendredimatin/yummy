package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

/**
 * @program: yummy
 * @description: list转换类
 * @author: Liu Hanyi
 * @create: 2019-02-27 14:22
 **/
@Converter(autoApply = true)
public class ListConverter2 implements AttributeConverter<List,String> {
    @Override
    public String convertToDatabaseColumn(List list) {
        String s = JSON.toJSONString(list);
        return s;
    }

    @Override
    public List convertToEntityAttribute(String s) {
        List list = JSONArray.parseArray(s);
        return list;
    }
}
