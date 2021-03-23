package com.sysu.mapper;


import com.sysu.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {

    List<Permission> queryPermissionsByRoleId(@Param("roleId") Integer roleId);

}
