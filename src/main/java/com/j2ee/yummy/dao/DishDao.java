package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.DishRepository;
import com.j2ee.yummy.model.canteen.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: dish的ｄａｏ
 * @author: Liu Hanyi
 * @create: 2019-02-27 18:46
 **/

@Repository
public class DishDao {

    @Autowired
    DishRepository dishRepository;

    public Dish insert(Dish dish){
        return dishRepository.save(dish);
    }

    public List<Dish> insert(List<Dish> dishes){
        return dishRepository.saveAll(dishes);
    }
}
