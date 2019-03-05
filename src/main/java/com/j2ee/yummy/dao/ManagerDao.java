package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.ManagerRepository;
import com.j2ee.yummy.Repository.UnauditedCanInfoRepository;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: 经理的dao类
 * @author: Liu Hanyi
 * @create: 2019-02-22 23:12
 **/


@Repository
public class ManagerDao {
    @Autowired
    UnauditedCanInfoRepository unauditedCanInfoRepository;
    @Autowired
    ManagerRepository managerRepository;

    public Manager login(long id,String password){
        return managerRepository.findByIdAndAndPassword(id,password);
    }

    public Manager insert(Manager manager){
        return managerRepository.save(manager);
    }


    public List<Manager> selectAllMs(){
        return managerRepository.findAll();
    }
}
