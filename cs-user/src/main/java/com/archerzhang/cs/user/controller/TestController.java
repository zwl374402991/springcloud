package com.archerzhang.cs.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * @author archerzhang
 * @date 2019.10.18
 */
@RestController
@RequestMapping("/test")
@Api("test")
public class TestController {

    @GetMapping("/hello")
    public String testGateway() {
        return "hello gateway 1!";
    }


}
