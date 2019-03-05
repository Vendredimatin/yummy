package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.ManagerDao;
import com.j2ee.yummy.dao.UnauditedCanInfoDao;
import com.j2ee.yummy.model.Manager;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.service.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @program: yummy
 * @description: manager 的业务逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-22 22:53
 *

*/

@Service
public class ManagerServiceImpl {
    @Autowired
    ManagerDao managerDao;
    @Autowired
    UnauditedCanInfoDao unauditedCanInfoDao;
    @Autowired
    CanteenService canteenService;

    public Manager login(long id,String password){
        return managerDao.login(id,password);
    }

    public boolean saveUnauditedCanInfo(UnauditedCanInfo canteenInfo){
        return unauditedCanInfoDao.insertUnauditedCanInfo(canteenInfo);
    }

    public List<Manager> getAllMs(){
        return managerDao.selectAllMs();
    }

    public List<UnauditedCanInfo> getAllUnaudited(){
        return unauditedCanInfoDao.getAllUnaudite();
    }

    public void pass(long canteenID){
        UnauditedCanInfo unauditedCanInfo = unauditedCanInfoDao.getUnauditedInfoByCanID(canteenID);

        Canteen canteen = canteenService.getCanteenByID(canteenID);
        canteen.setAddress(unauditedCanInfo.getAddress());
        canteen.setPhone(unauditedCanInfo.getPhone());
        canteen.setLandlordName(unauditedCanInfo.getLandlordName());
        canteen.setCanteenName(unauditedCanInfo.getCanteenName());
        canteen.setCategories(unauditedCanInfo.getCategories());

        unauditedCanInfoDao.delete(unauditedCanInfo.getId());
    }
}
