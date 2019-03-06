package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.BalanceDao;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: yummy
 * @description: 余额的业务逻辑类
 * @author: Liu Hanyi
 * @create: 2019-03-04 20:47
 **/
@Service
public class BalanceServiceImpl {
    @Autowired
    BalanceDao balanceDao;

    public Balance getBalance(long userID){
        return balanceDao.getBalance(userID);
    }

    public Balance getBalance(long userID, UserType userType){
        return balanceDao.getBalance(userID,userType);
    }

    public List<Balance> getBalances(UserType userType){
        return balanceDao.getBalancesByUserType(userType);
    }
}
