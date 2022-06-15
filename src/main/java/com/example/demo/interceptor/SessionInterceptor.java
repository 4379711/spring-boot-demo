package com.example.demo.interceptor;

import com.example.demo.constant.Constants;
import com.example.demo.exception.BaseException;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author YaLong
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求携带的令牌
        String headerToken = request.getHeader(Constants.TOKEN_HEADER);
        if (headerToken == null) {
            throw new BaseException("请求头中无令牌");
        }
        String[] s = headerToken.split("_", 2);
        if (s.length != 2) {
            throw new BaseException("令牌格式错误");
        }
        String username = s[0];
        RBucket<String> bucket = redissonClient.getBucket(Constants.TOKEN_PREFIX + username);
        String token = bucket.get();
        if (!s[1].equals(token)) {
            throw new BaseException("令牌已过期");
        }
        return true;
    }

}
