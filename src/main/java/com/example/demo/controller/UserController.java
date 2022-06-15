package com.example.demo.controller;

import com.example.demo.constant.R;
import com.example.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YaLong
 */
@RestController
@RequestMapping("/user")
@Api(tags = "后台")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @GetMapping("/login")
    public R<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = userService.login(username, password);
        return R.ok(token);
    }

    @GetMapping("/register")
    public R<String> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = userService.register(username, password);
        return R.ok(token);
    }

    @GetMapping("/test")
    public R<String> test() {
        return R.ok();
    }

}
