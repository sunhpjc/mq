package com.sunhp.activiti.we_chat_inter.springboot_enc;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * @author sunhp
 * @Description 加解密测试工具类
 * @Date 2022/9/26 10:23
 **/
public class SpringbootEncTest {
    public static void main(String[] args) {
        //明文
        String plaintext = "hcxq1LpR5TaJx4Qp";//ucn dev
        String encryption = encryption(plaintext);
        System.out.println("密文为：" + encryption);
        String decryption = decryption(encryption);
        System.out.println("明文为：" + decryption);
    }

    /**
     * 加密工具
     *
     * @param plaintext
     * @return
     */
    private static String encryption(String plaintext) {
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        //加密算法，写死即可
        config.setAlgorithm("PBEWithMD5AndDES");
        //加密使用salt设置
        config.setPassword("123456");
        //应用配置
        encryptor.setConfig(config);
        //加密
        return encryptor.encrypt(plaintext);
    }

    /**
     * 解密工具
     * @param ciphertext
     * @return
     */
    private static String decryption(String ciphertext) {
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        //加密算法，写死即可
        config.setAlgorithm("PBEWithMD5AndDES");
        //加密使用salt设置
        config.setPassword("123456");
        //应用配置
        encryptor.setConfig(config);
        //解密
        return encryptor.decrypt(ciphertext);
    }
}
