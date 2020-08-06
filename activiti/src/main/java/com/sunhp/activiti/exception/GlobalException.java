package com.sunhp.activiti.exception;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sunhp
 * @Description 全局异常
 * @Date 2020/4/13 11:05
 **/
@ControllerAdvice
@ResponseBody
public class GlobalException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);
    //前后端分离的情况下，做参数缺失异常处理，返回给前端一个友好提示
    /*
    * 参数缺失异常
    * */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultVO handleHttpMessageNotReadableException(MissingServletRequestParameterException ex){
        logger.error("请求参数缺失，{}",ex.getMessage());
        return new ResultVO<>(ResponseCodeEnum.REQUEST_PARAM_FAILED);
    }
    /*
    * 空指针异常
    * */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleNullPointerException(NullPointerException ex){
        logger.error("空指针异常，{}",ex.getMessage());
        return new ResultVO<>(ResponseCodeEnum.NULLPOINT);
    }
    /*
    * 拦截自定义异常
    * */
    @ExceptionHandler(MyException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleBusinessError(MyException ex) {
        String code = ex.getCode();
        String message = ex.getMsg();
        return new ResultVO(code, message);
    }
    /*
    * 所有异常都继承自exception，通常可以把这个放到最后，如果上面的异常都没有，那么异常归为预期外异常
    * */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleUnexpectedServer(Exception ex) {
        logger.error("系统异常：", ex);
        return new ResultVO("500", "系统异常，请jishi联系管理员");
    }
}
