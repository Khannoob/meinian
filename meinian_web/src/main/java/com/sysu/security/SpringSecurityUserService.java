package com.sysu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.pojo.Permission;
import com.sysu.pojo.Role;
import com.sysu.pojo.User;
import com.sysu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("springSecurityUserService")
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.queryUserByName(username);
        if (user != null) {
            String password = user.getPassword();
            //进行授权管理
            List<GrantedAuthority> list = new ArrayList<>();
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                }
            }
            return new org.springframework.security.core.userdetails.User(username, password, list);
        }
        return null;
    }
}
