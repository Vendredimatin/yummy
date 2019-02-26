/*
package com.j2ee.yummy.model;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.observer.Observer;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

*/
/**
 * @program: yummy
 * @description: 管理员类
 * @author: Liu Hanyi
 * @create: 2019-02-04 09:40
 **//*

@Entity
@Table(name = "manager")
@Getter
@Setter
public class Manager extends User implements Observer {
    @Transient
    @Autowired
    ManagerServiceImpl managerService;


    @Override
    public void update(UnauditedCanInfo canteenInfo) {
        managerService.saveUnauditedCanInfo(canteenInfo);
    }
}
*/
