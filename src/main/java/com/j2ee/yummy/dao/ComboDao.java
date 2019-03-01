package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.ComboRepository;
import com.j2ee.yummy.model.canteen.Combo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: 套餐的dao类
 * @author: Liu Hanyi
 * @create: 2019-02-27 18:47
 **/

@Repository
public class ComboDao {

    @Autowired
    ComboRepository comboRepository;

    public Combo insert(Combo combo){
        return comboRepository.save(combo);
    }

    public List<Combo> insert(List<Combo> combos){
        return comboRepository.saveAll(combos);
    }

    public List<Combo> getCombosByIDs(List<Long> ids){
        return comboRepository.findAllById(ids);
    }

    public Combo getComboByID(long id){
        return comboRepository.getOne(id);
    }
}
