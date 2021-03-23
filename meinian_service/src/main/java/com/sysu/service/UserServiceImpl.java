package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sysu.mapper.UserMapper;
import com.sysu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByName(String username) {
        return userMapper.queryUserByName(username);
    }
}
