package com.archerzhang.cs.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka注册中心
 * @author archerzhang
 * @date 2019.10.15
 */
@SpringBootApplication
@EnableEurekaServer
public class CsCenterApplication {

    /**
     * springboot 启动类
     * @param args ...
     */
    public static void main(String[] args) {
        SpringApplication.run(CsCenterApplication.class, args);
    }

}
