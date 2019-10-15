package com.archerzhang.csconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 配置中心
 * @author archerzhang
 * @date 2019.10.15
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class CsConfigApplication {

    /**
     * 启动类
     * @param args ...
     */
    public static void main(String[] args) {
        SpringApplication.run(CsConfigApplication.class, args);
    }

}
