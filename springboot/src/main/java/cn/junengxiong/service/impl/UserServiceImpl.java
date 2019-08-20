package cn.junengxiong.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import cn.junengxiong.bean.User;
import cn.junengxiong.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setPassword(username);
        user.setUsername(username);
        Set<String> roleList = new HashSet<>();
        Set<String> permissionsList = new HashSet<>();
        switch (username) {
        case "admin":
            roleList.add("admin");
            permissionsList.add("admin");
            break;
        case "consumer":
            roleList.add("consumer");
            permissionsList.add("consumer");
            break;
        default:
            roleList.add("guest");
            permissionsList.add("guest");
            break;
        }
        user.setRole(roleList);
        return user;
    }

}
