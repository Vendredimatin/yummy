package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.DishDao;
import com.j2ee.yummy.model.canteen.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: yummy
 * @description: dishService
 * @author: Liu Hanyi
 * @create: 2019-03-01 21:44
 **/
@Service
public class DishServiceImpl {
    @Autowired
    DishDao dishDao;

    public Dish getDishByID(long id){
        return dishDao.getDishByID(id);
    }

    public List<Dish> getDishesByIDs(List<Long> ids){
        return dishDao.getDishesByIDs(ids);
    }

    public void sell(long dishID,int num){
        Dish dish = dishDao.getDishByID(dishID);
        dish.setRemnants(dish.getRemnants() - num);
        dishDao.update(dish);
    }
}
