package com.sunhp.activiti.exception;


import com.sunhp.activiti.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author sunhp
 * @Description 自定义异常
 * @Date 2020/4/10 10:19
 **/
@Data
public class MyException extends RuntimeException{
    private String code;
    private String msg;
    /*只需要getter方法*/
    public  MyException(String msg){
        this("1002",msg);
    }
    public  MyException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public MyException(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getMsg();
    }
}
