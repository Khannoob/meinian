package com.sysu.mapper;

import com.sysu.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    List<Role> queryRolesByUserId(@Param("userId") Integer userId);
}
