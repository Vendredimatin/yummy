package com.j2ee.yummy;

import com.j2ee.yummy.Repository.MemberRepository;
import com.j2ee.yummy.Repository.OrderRepository;
import com.j2ee.yummy.dao.*;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.Menu;
import com.j2ee.yummy.model.order.MessageOrder;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.serviceImpl.OrderServiceImpl;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import com.j2ee.yummy.yummyEnum.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YummyApplicationTests {
    @Autowired
    UserDao userDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AddressDao addressDao;
    @Autowired
    CanteenDao canteenDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SpringTaskDemo springTaskDemo;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    BalanceDao balanceDao;
    @Autowired
    ManagerDao managerDao;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUser(){
        System.out.println(memberRepository.getOne(Long.valueOf(1)));
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


        canteen.setCategories("云南菜");

        canteenDao.insert(canteen);
    }

    @Test
    public void getCanteen(){
        Canteen canteen = canteenDao.getCanteenByID(1);
        System.out.println(canteen);
    }

    @Test
    public void getMenu(){
        long canteenID = 1;
        List<Menu> menus = menuDao.getMenusByCanID(1);
        System.out.println(menus);
    }

    @Test
    public void getOrders(){
        long memberID = 4;
        List<Order> orders = orderDao.getOrdersByMemID(4);
        System.out.println(orders);
    }

    @Test
    public void testTask(){
        MessageOrder messageOrder1 = new MessageOrder(1, OrderState.未支付,2);
        MessageOrder messageOrder2 = new MessageOrder(1, OrderState.派送中,3);

        springTaskDemo.appendOrder(messageOrder1);
        springTaskDemo.appendOrder(messageOrder2);
    }

    @Test
    public void pay(){
        orderService.pay(1);
    }

    @Test
    public void insertYummy(){
        Balance balance = new Balance();
        balance.setUserID(0);
        balance.setBalance(0);
        balance.setPassword("123");
        balance.setUserType(UserType.Yummy);
        balanceDao.insert(balance);
    }

    @Test
    public void insertBalance(){
        Balance balance = new Balance();
        balance.setUserID(4);
        balance.setPassword("123");
        balance.setBalance(1000);
        balance.setUserType(UserType.Canteen);
        balanceDao.insert(balance);
    }

    @Test
    public void insertManager(){
        Manager manager = new Manager();
        manager.setPassword("123");
        manager.setName("经理1");
        managerDao.insert(manager);
    }

    @Test
    public void selectOrders(){
        long memberID = 4;
        LocalDate startTime = LocalDate.of(2019,3,3);
        LocalDate endTime = LocalDate.of(2019,3,5);
        Page<Order> orders = orderService.memberSearch(4,startTime,endTime,200,0,"null", "所有",2);
        //List<Order> orders = orderRepository.findAllByMemberIDAndTimeBetween(memberID,startTime,endTime);
        System.out.println(orders.getContent());
    }

    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2860957268@qq.com");
        message.setTo("2860957268@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        javaMailSender.send(message);
    }
}


