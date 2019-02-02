package com.j2ee.yummy;

import com.j2ee.yummy.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YummyApplicationTests {
    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUser(){
        System.out.println(userDao.findOne(1));
    }

}

