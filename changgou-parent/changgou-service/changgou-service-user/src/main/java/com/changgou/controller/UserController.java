package com.changgou.controller;

import com.changgou.pojo.User;
import com.changgou.service.UserService;
import entity.BCrypt;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);

        return new Result<User>(true, StatusCode.OK,"查询客户成功",user);
    }

    @GetMapping("/login")
    public Result<User> login(String userName,String password){
        User userParm = new User();
        userParm.setPassword("$2a$10$FpSl6n6x7pl/AKu0LEcZDOlrg1Nt7sKtpgXKYUOSfPZFXV3.j4mJq");
        userParm.setUserName(userName);
        User user = userService.getUser(userParm);
        boolean checkResult = BCrypt.checkpw(password, user.getPassword());
        if(checkResult){
            return new Result<User>(true,StatusCode.OK,"登陆成功！",user);
        }else{
            return new Result<User>(false,StatusCode.LOGINERROR,"用户名或密码错误！");
        }
    }
}
