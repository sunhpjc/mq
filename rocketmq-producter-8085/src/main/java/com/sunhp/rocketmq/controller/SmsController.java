package com.sunhp.rocketmq.controller;

import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.service.SmsService;
import com.sunhp.rocketmq.vo.response.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Sms)表控制层
 *
 * @author makejava
 * @since 2020-12-31 11:27:57
 */
@RestController
@RequestMapping("sms")
@Api(value = "短信接口")
public class SmsController {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource
    private SmsService smsService;

    @PostMapping("selectOne")
    @ApiOperation(value = "根据id查询单条短信")
    public Sms selectOne(@RequestBody @Param("id") Long id) {
        return this.smsService.queryById(id);
    }

    @PostMapping("insertSmsBatch")
    @ApiOperation(value = "批量插入短信")
    public ResultVO insertSms(@RequestBody List<Sms> smsList){
        return smsService.insertBatch(smsList);
    }

    @GetMapping("buildSms")
    @ApiOperation(value = "测试使用，构造短信")
    public ResultVO buildSms(){
        return smsService.buildSms();
    }
}