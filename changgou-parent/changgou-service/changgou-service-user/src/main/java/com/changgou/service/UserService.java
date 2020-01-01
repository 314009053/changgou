package com.changgou.service;

import com.changgou.pojo.User;

public interface UserService {

    User getUserById(Integer id);

    User getUser(User user);
}
