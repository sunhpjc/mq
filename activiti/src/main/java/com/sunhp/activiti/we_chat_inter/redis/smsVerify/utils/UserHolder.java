package com.sunhp.activiti.we_chat_inter.redis.smsVerify.utils;

/**
 * @author sunhp
 * @Description 本地用户信息工具类
 * @Date 2022/11/15 20:12
 **/
public class UserHolder {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    /**
     * 保存用户信息
     * @param user
     */
    public static void saveUser(String user){
        tl.set(user);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static String getUser(){
        return tl.get();
    }

    /**
     * 移除用户信息
     * 注意使用完成之后一定要移除，否则容易造成内存泄露
     */
    public static void removeUser(){
        tl.remove();
    }
}
