package com.j2ee.yummy.model.canteen;

import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.converter.EntityConverter;
import com.j2ee.yummy.observer.Observer;
import com.j2ee.yummy.observer.Subject;
import com.j2ee.yummy.serviceImpl.ManagerServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: yummy
 * @description: 申请修改的餐厅信息
 * @author: Liu Hanyi
 * @create: 2019-02-22 23:09
 **/


@Getter
@Setter
@Table(name = "unauditedCanInfo")
@Entity
public class UnauditedCanInfo implements Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected long canteenID;
    protected String password;
    protected String canteenName;
    protected String landlordName;
    protected String phone;
    protected LocalDate time;
    @Convert(converter = EntityConverter.class)
    protected Address address;
    private String profile = "";
    private String categories;
    private int isAudited = 0;
    private int isPassed = 0;

    //存入数据库时，这个需要被忽略
    @Transient
    private List<Observer> observers = new ArrayList<>();

    public UnauditedCanInfo() {
    }

    public void attachAll(List<Manager> managers){
        observers.addAll(managers);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }


    @Override
    public void notifyManagers(ManagerServiceImpl managerService) {
        for (Observer observer:observers) {
            observer.update(this,managerService);
        }
    }

    @Override
    public String toString() {
        return "UnauditedCanInfo{" +
                "id=" + id +
                ", canteenID=" + canteenID +
                ", password='" + password + '\'' +
                ", canteenName='" + canteenName + '\'' +
                ", landlordName='" + landlordName + '\'' +
                ", phone='" + phone + '\'' +
                ", profile='" + profile + '\'' +
                ", categories='" + categories + '\'' +
                ", isAudited=" + isAudited +
                ", isPassed=" + isPassed + '\n' +
                ", address=" + address +
                '}';
    }
}
