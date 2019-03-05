package com.j2ee.yummy.observer;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;

public interface Observer {

    public void update(UnauditedCanInfo canteenInfo, ManagerServiceImpl managerService);

}
