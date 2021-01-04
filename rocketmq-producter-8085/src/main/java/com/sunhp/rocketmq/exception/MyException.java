package com.sunhp.rocketmq.exception;

import com.sunhp.rocketmq.enums.ResponseCodeEnum;

/**
 * @author sunhp
 * @Description
 * @Date 2020/12/31 14:25
 **/
public class MyException extends RuntimeException {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public MyException() {
        this("1001", "接口错误");
    }

    public MyException(String msg) {
        this("1001", msg);
    }

    public MyException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MyException(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getMsg();
    }
}
