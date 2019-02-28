package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.MenuRepository;
import com.j2ee.yummy.model.canteen.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: yummy
 * @description: menu的ｄａｏ类
 * @author: Liu Hanyi
 * @create: 2019-02-27 18:43
 **/
@Repository
public class MenuDao {

    @Autowired
    MenuRepository menuRepository;

    @Transactional
    public Menu insert(Menu menu){
        return menuRepository.save(menu);
    }

    public List<Menu> getMenusByCanID(long canteenID){
        return menuRepository.findAllByCanteenID(canteenID);
    }
}
