package com.j2ee.yummy.service;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;

public interface CanteenService {

    public boolean register(String password);

    public Canteen login(long id, String password);

    public boolean modify(UnauditedCanInfo canteen);
}
