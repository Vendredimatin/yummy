package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.MenuDao;
import com.j2ee.yummy.model.canteen.Combo;
import com.j2ee.yummy.model.canteen.Menu;
import com.j2ee.yummy.model.order.OrderItem;
import com.j2ee.yummy.yummyEnum.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @program: yummy
 * @description: 菜单的逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-27 18:49
 **/
@Service
public class MenuServiceImpl {

    @Autowired
    MenuDao menuDao;
    @Autowired
    ComboServiceImpl comboService;
    @Autowired
    DishServiceImpl dishService;

    public Menu save(Menu menu){
        return menuDao.insert(menu);
    }

    public List<Menu> getMenusByCanteenID(long canteenID){
        return menuDao.getMenusByCanID(canteenID);
    }

    public void sell(Set<OrderItem> orderItems){
        for (OrderItem o : orderItems) {
            long itemID = o.getItemID();
            int num = o.getNum();
            ItemCategory itemCategory = o.getItemCategory();

            if (itemCategory.equals(ItemCategory.Combo))
                comboService.sell(itemID,num);
            else dishService.sell(itemID,num);
        }
    }
}
