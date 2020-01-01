package com.changgou.service.impl;

import com.changgou.dao.UserMapper;
import com.changgou.pojo.User;
import com.changgou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

   // @Autowired
    //UserMapper userMapper;


    @Override
    public User getUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setUserName("zhangsan");
        user.setPassword("123456");
        return user;
    }

    @Override
    public User getUser(User user) {
        return user;
    }
}
