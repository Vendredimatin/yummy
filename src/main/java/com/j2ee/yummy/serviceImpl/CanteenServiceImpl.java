package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.CanteenDao;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.service.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: yummy
 * @description: 餐厅的业务逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-07 22:05
 **/

@Service
public class CanteenServiceImpl implements CanteenService {
    @Autowired
    CanteenDao canteenDao;

    private static final long START = 1000000L;
    @Override
    public boolean register(String password) {
        Canteen canteen = new Canteen();
        long id = getID();
        canteen.setId(id);
        canteen.setPassword(password);

        return canteenDao.insert(canteen);
    }

    @Override
    public Canteen login(long id, String password) {
        return canteenDao.login(id,password);
    }

    @Override
    public boolean modify(UnauditedCanInfo canteen) {
        //这里要提供给经理审批，由经理调用modify
        canteen.notify(canteen);
        return true;
    }

    private long getID(){
        long currentNum = canteenDao.getExistNum();
        return START+currentNum;
    }
}
