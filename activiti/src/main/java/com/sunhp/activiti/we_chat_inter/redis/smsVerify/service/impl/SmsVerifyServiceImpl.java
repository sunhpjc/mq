package com.sunhp.activiti.we_chat_inter.redis.smsVerify.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.service.impl.RedisUtils;
import com.sunhp.activiti.vo.ResultVO;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.dto.LoginFormDto;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.service.SmsVerifyService;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/11/11 11:30
 **/
@Service
@Slf4j
public class SmsVerifyServiceImpl implements SmsVerifyService {
    /**
     * 验证码登录redis key
     */
    private static final String LOGIN_CODE_KEY = "loginCode";
    /**
     * 用户信息key
     */
    private static final String LOGIN_USER_KEY = "loginUser";
    /**
     * 验证码过期时间
     */
    private static final int LOGIN_CODE_EXPIRE_TIME = 300;
    /**
     * 用户信息过期时间
     */
    private static final int LOGIN_USER_EXPIRE_TIME = 300;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public ResultVO sendCode(String phone) {
        //校验是否是移动号码（中国）
        if (!Validator.isMobile(phone)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "非移动手机号码");
        }
        //生成验证码
        final String verifyCode = RandomUtil.randomNumbers(6);
        redisUtils.set(LOGIN_CODE_KEY + phone, verifyCode, LOGIN_CODE_EXPIRE_TIME);
        log.info("动态验证码：{}，调用发送短信接口", verifyCode);
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    @Override
    public ResultVO login(LoginFormDto loginFormDto) {
        ResultVO resultVO;
        switch (loginFormDto.getLoginTypeEnum()) {
            case PHONE:
                resultVO = phoneLoginCheck(loginFormDto);
                break;
            case USER_NAME:
                resultVO = passwordLoginCheck(loginFormDto);
                break;
            default:
                resultVO = new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "不存在此登录类型");
                break;
        }
        if (!ResponseCodeEnum.SUCCESS.getCode()
            .equals(resultVO.getCode())) {
            return resultVO;
        }
        //以上校验通过，生成token
        log.info("校验通过，生成token");
        String token = IdUtil.fastSimpleUUID();
        String userInfo = "用户详细信息";
        redisUtils.set(LOGIN_USER_KEY + token, userInfo, LOGIN_USER_EXPIRE_TIME);
        log.info("返回token");
        return new ResultVO(ResponseCodeEnum.SUCCESS, token);
    }

    /**
     * 校验电话验证码
     *
     * @param loginFormDto
     * @return
     */
    private ResultVO phoneLoginCheck(LoginFormDto loginFormDto) {
        String phone = loginFormDto.getPhone();
        String verifyCode = loginFormDto.getVerifyCode();
        if (StringUtils.isEmpty(verifyCode)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "验证码不能为空");
        }
        //校验是否是移动号码（中国）
        if (!Validator.isMobile(phone)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "手机号格式错误");
        }
        String redisVerifyCode = redisUtils.get(LOGIN_CODE_KEY + phone);
        if (StringUtils.isEmpty(redisVerifyCode)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "验证码已过期");
        }
        //校验验证码
        if (!verifyCode.equals(redisVerifyCode)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "验证码不正确");
        }
        //通过手机号查询用户
        log.info("通过手机号查询用户是否存在");
        log.info("用户存在，发送短信 or 用户不存在，抛出用户不存");
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 校验用户密码
     *
     * @param loginFormDto
     * @return
     */
    private ResultVO passwordLoginCheck(LoginFormDto loginFormDto) {
        String phone = loginFormDto.getPhone();
        String password = loginFormDto.getPassword();
        if (StringUtils.isEmpty(password)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "密码不能为空");
        }
        //校验是否是移动号码（中国）
        if (!Validator.isMobile(phone)) {
            return new ResultVO(ResponseCodeEnum.PARAMS_ERROR.getCode(), "手机号格式错误");
        }
        log.info("校验手机号和密码是否匹配");
        log.info("用户匹配，登录成功 or 用户不匹配，登录失败");
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

}
