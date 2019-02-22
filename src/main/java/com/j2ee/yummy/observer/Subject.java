package com.j2ee.yummy.observer;

import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;

public interface Subject {

    public void attach(Observer observer);

    public void notify(UnauditedCanInfo canteenInfo);
}
