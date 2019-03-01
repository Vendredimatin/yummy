package com.j2ee.yummy.service;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;

import java.util.List;

public interface CanteenService {

    public Canteen register(Canteen canteen);

    public Canteen login(long id, String password);

    public boolean modify(UnauditedCanInfo canteen);

    public Canteen getCanteenByID(long id);

    public List<Canteen> getAll();
}
