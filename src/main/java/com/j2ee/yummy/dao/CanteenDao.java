package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.CanteenRepository;
import com.j2ee.yummy.model.canteen.Canteen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: 餐厅的dao类
 * @author: Liu Hanyi
 * @create: 2019-02-07 22:09
 **/

@Repository
public class CanteenDao {
    @Autowired
    CanteenRepository canteenRepository;

    public long getExistNum(){
        return canteenRepository.count();
    }

    public Canteen insert(Canteen canteen){
       return canteenRepository.save(canteen);
    }

    public Canteen login(long id, String password){
        return canteenRepository.findCanteenByIdAndPassword(id,password);
    }

    public boolean update(Canteen canteen){
        try{
            canteenRepository.save(canteen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Canteen getCanteenByID(long id){
        return canteenRepository.getOne(id);
    }

    public List<Canteen> getAll(){ return canteenRepository.findAll();}
}
