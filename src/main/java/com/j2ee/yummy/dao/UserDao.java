package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.UserRepository;
import com.j2ee.yummy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public User findOne(Integer id){
        return userRepository.getOne(Long.valueOf(id));
    }
}
