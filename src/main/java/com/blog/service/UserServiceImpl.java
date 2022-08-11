package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.dao.UserMapper;
import com.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","nickname","username","password","email","avatar","type","create_time","update_time");
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        return userMapper.selectOne(queryWrapper);
    }
}
