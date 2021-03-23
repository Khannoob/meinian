package com.sysu.service;

import com.sysu.pojo.User;

public interface UserService {
    User queryUserByName(String username);
}
