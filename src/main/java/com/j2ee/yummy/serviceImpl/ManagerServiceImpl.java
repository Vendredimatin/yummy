/*
package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.ManagerDao;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

*/
/**
 * @program: yummy
 * @description: manager 的业务逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-22 22:53
 **//*


@Service
public class ManagerServiceImpl {
    @Autowired
    ManagerDao managerDao;

    public boolean saveUnauditedCanInfo(UnauditedCanInfo canteenInfo){
        return managerDao.insertUnauditedCanInfo(canteenInfo);
    }

    public List<Manager> getAllMs(){
        return managerDao.selectAllMs();
    }


}
*/
