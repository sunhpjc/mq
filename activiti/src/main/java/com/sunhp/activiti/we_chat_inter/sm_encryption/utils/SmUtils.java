package com.sunhp.activiti.we_chat_inter.sm_encryption.utils;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author sunhp
 * @Description 国密工具类
 * @Date 2024/12/5 14:02
 **/
public class SmUtils {
    /**
     * sm4对称加密使用key,必须是16字节(128位)
     */
    private static final String SM_KEY = "JF8EB0zMRKtkYzkM";

    /**
     * 指明加密算法和秘钥
     */
    private static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", SM_KEY.getBytes());

    /**
     * sm4加密字符串
     * @param plaintext
     * @return
     */
    public static String encryptSm4(String plaintext) {
        if (StringUtils.isBlank(plaintext)) {
            return "";
        }
        return sm4.encryptHex(plaintext);
    }

    /**
     * sm4解密字符串
     * @param ciphertext
     * @return
     */
    public static String decryptSm4(String ciphertext) {
        if (StringUtils.isBlank(ciphertext)) {
            return "";
        }
        return sm4.decryptStr(ciphertext);
    }
}
