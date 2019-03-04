package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.BalanceRepository;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: yummy
 * @description: 余额的dao层
 * @author: Liu Hanyi
 * @create: 2019-03-02 20:40
 **/
@Repository
public class BalanceDao {

    @Autowired
    BalanceRepository balanceRepository;

    public Balance getBalance(long userID, UserType userType){
        return balanceRepository.findByUserIDAndUserType(userID,userType);
    }

    public Balance getBalance(long userID){
        return balanceRepository.findBalanceByUserID(userID);
    }

    public void updateBalance(Balance balance){
        balanceRepository.saveAndFlush(balance);
    }

    public Balance insert(Balance balance){
        return balanceRepository.save(balance);
    }
}
