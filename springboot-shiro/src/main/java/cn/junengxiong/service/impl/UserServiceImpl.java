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
        user.setUsername(username);
        Set<String> roleList = new HashSet<>();
        Set<String> permissionsList = new HashSet<>();
        switch (username) {
        case "admin":
            roleList.add("admin");
            user.setPassword("admin");
            permissionsList.add("user:add");
            permissionsList.add("user:delete");
            break;
        case "consumer":
            roleList.add("consumer");
            user.setPassword("consumer");
            permissionsList.add("consumer:modify");
            break;
        default:
            roleList.add("guest");
            user.setPassword("565dd969076eef0ac3f9d49aa61e9489");
            break;
        }
        user.setRole(roleList);
        user.setPermission(permissionsList);
        return user;
    }

}
