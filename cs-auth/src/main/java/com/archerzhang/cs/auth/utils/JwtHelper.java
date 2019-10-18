package com.archerzhang.cs.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * @author archerzhang
 * @date 2019.10.16
 */
@Slf4j
public class JwtHelper {

    /**
     *     - 用户编号
     *    - 用户名
     *   - 客户端信息（变长参数），目前包含浏览器信息，用于客户端拦截器校验，防止跨域非法访问
     * 生成JWT字符串
     * 格式：A.B.C
     * A-header头信息
     * B-payload 有效负荷
     * C-signature 签名信息 是将header和payload进行加密生成的
     */
    public static String generateJWT(Map<String, Object> map) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 添加构成JWT的参数
        // 获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);

        // 将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET);

        // 使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(map)
                .signWith(signatureAlgorithm, signingKey);

        // 添加token过期时间
        if (SecretConstant.EXPIRESSECOND >= 0) {
            long expMillis = nowTimeMillis + SecretConstant.EXPIRESSECOND;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate).setNotBefore(now);
            log.info("expDate: {}", expDate);
            log.info("now: {}", now);
        }

        return builder.compact();
    }

    /**
     * jwt解析
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;

        try {
            if (StringUtils.isNotBlank(jsonWebToken)) {
                // 解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
            } else {
                log.warn("[JWTHelper]-json web token is null");
            }
        } catch (Exception e) {
            log.error("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
        }
        return claims;
    }

//    /**
//     * 校验JWT是否有效
//     * @param jsonWebToken jwt
//     * freshToken-刷新后的jwt
//     * userName-用户名
//     * account-用户账号
//     * email-邮箱
//     * userAgent-客户端浏览器信息
//     */
//    public static String validateLogin(String jsonWebToken) {
//        Map<String, Object> retMap = null;
//        Claims claims = parseJWT(jsonWebToken);
//        if (claims != null) {
//            // 解密客户编号
//            String decryptUserId = AESSecretUtil.decryptToStr((String)claims.get("userId"), SecretConstant.DATAKEY);
//            // 加密后的用户账号
//            retMap.put("account", decryptUserId);
//            retMap.put("username", claims.get("username"));
//            retMap.put("email", claims.get("email"));
//            retMap.put("userAgent", claims.get("userAgent"));
//
//            // 刷新jwt
//            // retMap.put("freshToken", generateJWT(decryptUserId, (String) claims.get("account"), (String) claims.get("userName"), (String) claims.get("email"), (String) claims.get("userAgent")));
//        } else {
//            log.warn("[JWTHelper]-JWT解析出claims为空");
//        }
//        return retMap != null ? JSONObject.toJSONString(retMap) : null;
//    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("zhang", "archer");
        map.put("meng", "rider");
        map.put("saber", "test2");
//        map.put("customerId", "2");
//        map.put("sysType", "1");
        String jsonWebKey = generateJWT(map);
        System.out.println(jsonWebKey);
        Claims claims = parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW5nIjoicmlkZXIiLCJzYWJlciI6InRlc3QyIiwibmJmIjoxNTcxMjE1NDgyLCJ6aGFuZyI6ImFyY2hlciIsImV4cCI6MTU3MTIxNzI4Mn0.7SIeSnl4epLuvsk1NEbPLO82XpF4GVwssrkvFwyVfqM");
        System.out.println(claims);
    }

}
