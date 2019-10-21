package com.archerzhang.cs.gateway.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * feign调用其他微服务接口
 * @author archerzhang
 * @date 2019.10.18
 *
 *  FeignClient(value = "auth-service")
 *      这里的value对应调用服务的spring.applicatoin.name
 */
@Component
@FeignClient(value = "auth-service")
public interface AuthClientService {


    @PostMapping("/auth-service/jwt/generateJWT")
    String generateJWT(@RequestBody Map<String, Object> map);

    @GetMapping("/auth-service/jwt/parseJWT")
    Map<String, Object> parseJWT(@RequestParam("jsonWebToken") String jsonWebToken);

}
