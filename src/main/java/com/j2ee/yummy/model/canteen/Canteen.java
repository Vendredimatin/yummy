package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.observer.Observer;
import com.j2ee.yummy.observer.Subject;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: yummy
 * @description: 餐厅类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:53
 **/
@Getter
@Setter
public class Canteen implements Subject {
    protected long id;
    protected String password;
    protected String canteenName;
    protected String landlordName;
    protected String phone;
    protected Address address;
    private long menuID;
    private String profile;
    private List<CanteenCategory> categories;
    //存入数据库时，这个需要被忽略

    private List<Observer> observers;

    @Autowired
    ManagerServiceImpl managerService;

    public Canteen() {
        attachAll();
    }

    public Canteen(long id, String password, String canteenName, String landlordName, String phone, Address address, long menuID, String profile,List<CanteenCategory> canteenCategories) {
        this.id = id;
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
        this.menuID = menuID;
        this.profile = profile;
        this.categories = canteenCategories;

        attachAll();
    }

    public Canteen(String password, String canteenName, String landlordName, String phone, Address address, long menuID, String profile,List<CanteenCategory> canteenCategories) {
        this.password = password;
        this.canteenName = canteenName;
        this.landlordName = landlordName;
        this.phone = phone;
        this.address = address;
        this.menuID = menuID;
        this.profile = profile;
        this.categories = canteenCategories;

        attachAll();
    }

    @Override
    public String toString() {
        return "Canteen{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", canteenName='" + canteenName + '\'' +
                ", landlordName='" + landlordName + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", menuID=" + menuID +
                ", profile='" + profile + '\'' +
                '}';
    }

    public void attachAll(){
        //这里的话应该能在数据库层优化自动实现，多对多
        List<Manager> managers = managerService.getAllMs();
        observers.addAll(managers);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }


    @Override
    public void notify(UnauditedCanInfo canteenInfo) {
        for (Observer observer:observers) {
            observer.update(canteenInfo);
        }
    }
}
