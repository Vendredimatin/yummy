package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.j2ee.yummy.yummyEnum.CanteenCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

/**
 * @program: yummy
 * @description: list转换类
 * @author: Liu Hanyi
 * @create: 2019-02-26 15:24
 **/
@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<CanteenCategory>,String> {
    @Override
    public String convertToDatabaseColumn(List<CanteenCategory> canteenCategories) {
        String s = JSON.toJSONString(canteenCategories);
        return s;
    }

    @Override
    public List<CanteenCategory> convertToEntityAttribute(String s) {
        List<CanteenCategory> canteenCategories = JSONArray.parseArray(s,CanteenCategory.class);
        return canteenCategories;
    }
}
