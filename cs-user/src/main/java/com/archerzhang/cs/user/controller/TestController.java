package com.archerzhang.cs.user.controller;

import com.archerzhang.cs.user.config.AuthClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试controller
 * @author archerzhang
 * @date 2019.10.18
 */
@RestController
@RequestMapping("/test")
@Api("test")
public class TestController {

    @Autowired
    AuthClientService authClientService;


    @PostMapping("test")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
    })
    public String test(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        String jwt = authClientService.generateJWT(map);
        return jwt;
    }


}
