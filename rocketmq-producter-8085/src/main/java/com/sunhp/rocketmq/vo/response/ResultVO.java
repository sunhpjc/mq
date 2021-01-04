package com.sunhp.rocketmq.vo.response;

import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author sunhp
 * @Description
 * @Date 2020/12/31 14:19
 **/
@Data
public class ResultVO<T> {
    private String code;
    private String message;
    private T body;

    public ResultVO(String code,String msg){
        this.code = code;
        this.message = msg;
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
