package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.ComboDao;
import com.j2ee.yummy.model.canteen.Combo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: yummy
 * @description: comboServuce
 * @author: Liu Hanyi
 * @create: 2019-03-01 21:47
 **/

@Service
public class ComboServiceImpl {
    @Autowired
    ComboDao comboDao;

    public Combo getComboByID(long id){
        return comboDao.getComboByID(id);
    }

    public List<Combo> getCombosByIDs(List<Long> ids){
        return comboDao.getCombosByIDs(ids);
    }
}
