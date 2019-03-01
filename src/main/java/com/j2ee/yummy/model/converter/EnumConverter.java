package com.j2ee.yummy.model.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.yummy.yummyEnum.ItemCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @program: yummy
 * @description: EnumConverter
 * @author: Liu Hanyi
 * @create: 2019-02-27 14:28
 **/
@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<ItemCategory,String> {
    @Override
    public String convertToDatabaseColumn(ItemCategory dishCategory) {
        String s = JSON.toJSONString(dishCategory);
        return s;
    }

    @Override
    public ItemCategory convertToEntityAttribute(String s) {
        ItemCategory dishCategory = JSONObject.parseObject(s, ItemCategory.class);
        return dishCategory;
    }
}
