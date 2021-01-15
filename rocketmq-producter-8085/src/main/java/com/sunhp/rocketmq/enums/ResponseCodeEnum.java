package com.sunhp.rocketmq.enums;

import com.sunhp.rocketmq.exception.MyException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sunhp
 * @Description
 * @Date 2020/12/31 14:20
 **/
public enum ResponseCodeEnum {
    SUCCESS("1000","请求成功"),
    FAILED("1001","请求失败"),
    PARAM_ERROR("1002","参数错误"),
    REQUEST_PARAM_FAILED("2000","请求参数缺失"),
    NULLPOINT("5000","空指针异常"),
    UNEXPECTED_EXCEPTION("500", "系统发生异常，请联系管理员！"),

    //三方组件
    JEDIS_ERROR("9000","获取jedis实例失败"),
    REDIS_ID_ERROR("9001","redis生成唯一ID失败"),
    ROCKETMQ_ERROR("9010","rocketMQ异常")
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
        throw new MyException(ResponseCodeEnum.PARAM_ERROR.getCode(),"枚举类型错误");
    }
}
