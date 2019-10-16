package com.archerzhang.cs.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加密工具类
 * @author archerzhang
 * @date 2019.10.16
 */
@Slf4j
public class AESSecretUtil {

    // 密钥的大小
    private static final int KEYSIZE = 128;

    /**
     * AES加密
     * @param data 待加密内容
     * @param key 加密秘钥
     */
    public static byte[] encrypt(String data, String key) {
        if (StringUtils.isNotBlank(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                // 选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEYSIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

                // 创建密码器
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = data.getBytes("utf-8");

                // 初始化
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                // 加密
                byte[] result = cipher.doFinal(byteContent);

                return result;
            } catch (Exception e) {
                log.error("Exception: {}", e);
            }
        }
        return null;
    }

    /**
     * AES加密，返回String
     * @param date 待加密内容
     * @param key 加密秘钥
     * @return
     */
    public static String encryptToStr(String date, String key) {
        return StringUtils.isNotBlank(date) ? parseByte2HexStr(encrypt(date, key)) : null;
    }


    /**
     * AES解密
     * @param data 待解密字节数组
     * @param key 秘钥
     * @return
     */
    public static byte[] decrypt(byte[] data, String key) {
        if (ArrayUtils.isNotEmpty(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEYSIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] result = cipher.doFinal(data);
                return result;
            } catch (Exception e) {
                log.error("Exception: {}", e);
            }
        }
        return null;
    }

    /**
     * AES解密,返回String
     * @param enCryptData 待解密字节数组
     * @param key 秘钥
     * @return
     */
    public static String decryptToStr(String enCryptData, String key) {
        return StringUtils.isNotBlank(enCryptData) ? new String(decrypt(parseHexStr2Byte(enCryptData), key)) : null;
    }

    /**
     * 将二进制转换为十六进制
     * @param buf 二进制数组
     * @return
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将十六进制转换为二进制
     * @param hexStr 16进制字符串
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }


}
