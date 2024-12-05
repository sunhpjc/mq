package com.sunhp.activiti.we_chat_inter.sm_encryption.test;

import com.sunhp.activiti.we_chat_inter.sm_encryption.utils.SmUtils;

/**
 * @author sunhp
 * @Description
 * @Date 2024/12/5 14:22
 **/
public class SmMainTest {
    public static void main(String[] args) {
        String str = "123456fhuehf____.....";
        String encryptSm4 = SmUtils.encryptSm4(str);
        System.out.println("加密后密文为:" + encryptSm4);
        String decryptSm4 = SmUtils.decryptSm4(encryptSm4);
        System.out.println("解密后明文为:" + decryptSm4);
    }
}
