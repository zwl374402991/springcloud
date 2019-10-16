package com.archerzhang.cs.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * auth-service
 * @author archerzhang
 * @date 2019.10.16
 */
@SpringBootApplication
@EnableEurekaClient
public class CsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsAuthApplication.class, args);
    }

}
