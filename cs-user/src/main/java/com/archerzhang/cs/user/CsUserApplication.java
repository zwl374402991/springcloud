package com.archerzhang.cs.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 模拟用户项目
 * @author archerzhang
 * @date 2019.10.18
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.archerzhang.cs.user.config")
public class CsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsUserApplication.class, args);
    }

}
