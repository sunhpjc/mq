package com.sunhp.activiti.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.service.UserService;
import com.sunhp.activiti.service.impl.RedisService;
import com.sunhp.activiti.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description 测试Redis
 * @Date 2020/4/15 15:52
 **/
@RestController
@RequestMapping(value = "redis")
public class TestRedisController {
    private static final Logger logger = LoggerFactory.getLogger(TestRedisController.class);

    @Resource
    private RedisService redisService;
    @Resource
    private UserService userService;
    /*
    * 测试redis
    * */
    @RequestMapping(value = "string_test")
    public ResultVO<User> testRedis(){
        //操作string类型
        /*redisService.setString("wechat","sunhp");
        logger.info("我的微信：{}",redisService.getString("wechat"));*/

        //操作实体
        User user = userService.queryById(12);
        //redisService.setString("userInfo", JSON.toJSONString(user));
        logger.info("用户信息：{}",redisService.getString("userInfo"));
        return new ResultVO<>(ResponseCodeEnum.SUCCESS, JSON.parseObject(redisService.getString("userInfo"),User.class));
    }
}
