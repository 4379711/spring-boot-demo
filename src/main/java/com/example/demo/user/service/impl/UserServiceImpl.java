package com.example.demo.user.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.example.demo.constant.Constants;
import com.example.demo.exception.BaseException;
import com.example.demo.user.domain.User;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.service.UserService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author YaLong
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    private String flushRedisToken(String username) {
        //登录成功，生成token
        RBucket<String> bucket = redissonClient.getBucket(Constants.TOKEN_PREFIX + username);
        String token = UUID.randomUUID().toString();
        bucket.set(token, Constants.TOKEN_EXPIRE, TimeUnit.MINUTES);
        return username + "_" + token;
    }

    @Override
    public String login(String username, String password) {
        User one = ChainWrappers.lambdaQueryChain(baseMapper)
                .eq(User::getAccount, username)
                .one();
        if (one == null) {
            throw new BaseException("用户不存在");
        }
        String oldPassword = one.getPassword();
        String salt = one.getSalt();
        String s = SecureUtil.md5().digestHex(password + salt);
        if (!s.equals(oldPassword)) {
            throw new BaseException("密码错误");
        }
        return this.flushRedisToken(username);
    }

    @Override
    public String register(String username, String password) {
        Long count = ChainWrappers.lambdaQueryChain(baseMapper)
                .eq(User::getAccount, username)
                .count();
        if (count > 0) {
            throw new BaseException("用户已存在");
        }
        String salt = UUID.randomUUID().toString();
        String s = SecureUtil.md5().digestHex(password + salt);
        User user = new User();
        user.setAccount(username);
        user.setPassword(s);
        user.setSalt(salt);
        this.save(user);
        return this.flushRedisToken(username);
    }

}




