package com.j2ee.yummy;

import com.j2ee.yummy.dao.AddressDao;
import com.j2ee.yummy.dao.CanteenDao;
import com.j2ee.yummy.dao.UserDao;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.yummyEnum.CanteenCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YummyApplicationTests {
    @Autowired
    UserDao userDao;
    @Autowired
    AddressDao addressDao;
    @Autowired
    CanteenDao canteenDao;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testUser(){
        System.out.println(userDao.findOne(1));
    }

    @Test
    public void testAddresses(){
        List<Address> addresses = addressDao.getAddressesByMemID(4);
        System.out.println(addresses);
    }

    @Test
    public void testAddress(){
        Canteen canteen = new Canteen();
        canteen.setPassword("123");
        canteen.setCanteenName("大排档");
        canteen.setLandlordName("lhy");
        canteen.setPhone("13218027718");
        Address address = new Address();
        address.setProvince("江苏省");
        address.setCity("南京市");
        address.setDistrict("鼓楼区");
        canteen.setAddress(address);
        List<CanteenCategory> canteenCategories = new ArrayList<>();
        canteenCategories.add(CanteenCategory.云南菜);
        canteenCategories.add(CanteenCategory.川菜);
        canteenCategories.add(CanteenCategory.江苏菜);

        canteen.setCategories(canteenCategories);

        canteenDao.insert(canteen);
    }

    @Test
    public void getCanteen(){
        Canteen canteen = canteenDao.getCanteenByID(1);
        System.out.println(canteen);
    }
}

