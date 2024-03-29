package com.sunhp.activiti.enums;

import com.sunhp.activiti.exception.MyException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sunhp
 * @Description 响应枚举类型
 * @Date 2020/4/10 17:23
 **/
public enum ResponseCodeEnum {
    SUCCESS("0000","请求成功"),
    FAILED("1001","请求失败"),
    REQUEST_PARAM_FAILED("2000","请求参数缺失"),
    NULLPOINT("5000","空指针异常"),
    UNEXPECTED_EXCEPTION("500", "系统发生异常，请联系管理员！"),

    //redis码值
    REDIS_JEDIS_POOL_ERROR("7001", "jedis pool获取连接异常"),
    //参数检验错误
    PARAMS_ERROR("8001", "参数检验错误"),
    //登录异常
    LOGIN_ERROR("9001","用户登录异常"),

    ;
    ResponseCodeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseCodeEnum getByCode(String code){
        if(StringUtils.isNotEmpty(code)){
            for (ResponseCodeEnum ResVO: values()
                 ) {
                if(ResVO.code.equals(code)){
                    return ResVO;
                }
            }
        }
        throw new MyException("1003","枚举类型错误");
    }

    public static void main(String[] args) {
        System.out.println(ResponseCodeEnum.getByCode("1002"));
    }
}
