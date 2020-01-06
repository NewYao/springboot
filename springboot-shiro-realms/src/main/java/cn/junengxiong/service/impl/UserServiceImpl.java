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
        if ("username".equals(username)) {
            Set<String> roleList = new HashSet<>();
            Set<String> permissionsList = new HashSet<>();
            roleList.add("admin");
            permissionsList.add("admin");
            user.setUsername("username");
            user.setPassword("b6d3d2a23b4d5e313d3f2efe3cda2614");
            user.setRole(roleList);
            user.setPermission(permissionsList);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = new User();
        if ("email".equals(email)) {
            Set<String> roleList = new HashSet<>();// 角色
            Set<String> permissionsList = new HashSet<>();// 权限
            roleList.add("consumer");
            permissionsList.add("consumer");
            user.setUsername("email");
            user.setPassword("5fb06af6320cb2f9f090c4f9e1337ffb");
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User findByPhone(String phone) {
        User user = new User();
        Set<String> roleList = new HashSet<>();// 角色
        Set<String> permissionsList = new HashSet<>();// 权限
        if ("phone".equals(phone)) {
            user.setUsername("guest");
            user.setPassword("94585d3850aa9fe1156d272ce3447a07");
            roleList.add("guest");
            permissionsList.add("guest");
            return user;
        } else {
            return null;
        }
    }

}
