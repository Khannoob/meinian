package com.sysu.service;

import com.sysu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("userService")
public class UserService implements UserDetailsService {
    static Map<String,User> map = new HashMap<>();
    static {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("$2a$10$lpM9hZTk4hMMJa.PxUYjue2AgUsF4wex5FGAde2YQ3HiBKnE/JVdO");
        user1.setTelephone("123");
        User user2 = new User();
        user2.setUsername("zhangsan");
        user2.setPassword("$2a$10$lpM9hZTk4hMMJa.PxUYjue2AgUsF4wex5FGAde2YQ3HiBKnE/JVdO");
        user2.setTelephone("321");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        User user = map.get(username);
        if (user!=null){
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            grantedAuthorityList.add(new SimpleGrantedAuthority("add"));
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            grantedAuthorityList.add(new SimpleGrantedAuthority("query"));
            grantedAuthorityList.add(new SimpleGrantedAuthority("delete"));
            return new org.springframework.security.core.userdetails.User(username,user.getPassword()
                    , grantedAuthorityList);
        }
        return null;
    }
}
