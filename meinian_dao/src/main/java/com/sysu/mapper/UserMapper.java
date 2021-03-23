package com.sysu.mapper;

import com.sysu.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User queryUserByName(@Param("username") String username);
}
