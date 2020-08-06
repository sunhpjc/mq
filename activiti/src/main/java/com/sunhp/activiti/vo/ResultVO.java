package com.sunhp.activiti.vo;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author sunhp
 * @Description 封装响应体
 * @Date 2020/4/13 9:39
 **/
@Data
public class ResultVO<T> {
    private String code;
    private String message;
    private T body;

/*    public ResultVO(T data){
        this(ResponseCodeEnum.SUCCESS,data);
    }*/
    public ResultVO(String code,String msg){
        this.code = code;
        this.message = msg;
    }
    public ResultVO(String code,String msg,T data){
        this.code = code;
        this.message = msg;
        this.body = data;
    }
    public ResultVO(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMsg();
    }
    public ResultVO(ResponseCodeEnum responseCodeEnum,T data) {
        this.code = responseCodeEnum.getCode();
        this.message= responseCodeEnum.getMsg();
        this.body = data;
    }
}
