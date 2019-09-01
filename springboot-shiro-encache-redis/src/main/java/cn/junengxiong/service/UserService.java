package cn.junengxiong.service;

import cn.junengxiong.bean.User;

public interface UserService {
    User findByUsername(String username);
}
