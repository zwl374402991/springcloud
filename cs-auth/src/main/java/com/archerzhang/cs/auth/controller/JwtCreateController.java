package com.archerzhang.cs.auth.controller;

import com.archerzhang.cs.auth.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * 生成jwt
 * @author archerzhang
 * @date 2019.10.16
 */
@Slf4j
@RestController
@RequestMapping("/jwt")
public class JwtCreateController {

    /**
     * 生成jwt
     * @param map
     * @return
     */
    @PostMapping("/generateJWT")
    public String generateJWT(@RequestBody Map<String, Object> map) {
        String jwt = JwtHelper.generateJWT(map);
        return jwt;
    }

    /**
     * 解析jwt
     * @param jsonWebToken
     * @return
     */
    @GetMapping("/parseJWT")
    public Map<String, Object> parseJWT(@RequestParam("jsonWebToken") String jsonWebToken) {
        Claims claims = JwtHelper.parseJWT(jsonWebToken);
        log.info("claims: {}", claims);
        return claims;
    }

    /**
     * 刷新jwt
     * @param jsonWebToken
     * @return
     */
    @PostMapping(value = "/refreshJWT")
    public String refreshJWT(@RequestParam(name = "jwt") String jsonWebToken) {
        Claims claims = JwtHelper.parseJWT(jsonWebToken);
        long endTime = claims.getExpiration().getTime();
        long now = System.currentTimeMillis();
        if(endTime - now >= 0){
            return JwtHelper.generateJWT(claims);
        }else {
            log.error("jwt 刷新失败");
            return null;
        }
    }

}
