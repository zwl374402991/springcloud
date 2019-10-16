package com.archerzhang.cs.auth.utils;

/**
 * jwt使用的常量值
 * @author archerzhang
 * @date 2019.10.16
 */
public class SecretConstant {

    // 签名密钥 自定义
    public static final String BASE64SECRET = "0123456789ABCDEF";

    // 超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    // 用于JWT加密的密钥 自定义
    public static final String DATAKEY = "0123456789ABCDEF";
}
