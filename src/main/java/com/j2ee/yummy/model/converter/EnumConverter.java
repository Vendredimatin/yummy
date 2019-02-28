package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.yummyEnum.DishCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @program: yummy
 * @description: EnumConverter
 * @author: Liu Hanyi
 * @create: 2019-02-27 14:28
 **/
@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<DishCategory,String> {
    @Override
    public String convertToDatabaseColumn(DishCategory dishCategory) {
        String s = JSON.toJSONString(dishCategory);
        return s;
    }

    @Override
    public DishCategory convertToEntityAttribute(String s) {
        DishCategory dishCategory = JSONObject.parseObject(s,DishCategory.class);
        return dishCategory;
    }
}
