package com.archerzhang.cs.user.controller;

import com.archerzhang.cs.user.config.AuthClientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟系统登陆
 * @author archerzhang
 * @date 2019.10.21
 */
@RestController
@RequestMapping("/login")
@Api("login")
public class LoginController {

    @Autowired
    private AuthClientService authClientService;

    @PostMapping("/logging")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("username",username);
        logMap.put("password",password);
        return authClientService.generateJWT(logMap);

    }
}
