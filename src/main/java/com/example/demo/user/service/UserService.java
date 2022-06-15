package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author YaLong
 */
public interface UserService extends IService<User> {

    String login(String username, String password);

    String register(String username, String password);
}
