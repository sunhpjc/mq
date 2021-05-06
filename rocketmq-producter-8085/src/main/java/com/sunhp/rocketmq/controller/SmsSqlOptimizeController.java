package com.sunhp.rocketmq.controller;

import com.sunhp.rocketmq.service.SmsSqlOptimizeService;
import com.sunhp.rocketmq.vo.response.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description SQL优化Contoller
 * @Date 2021/4/19 15:34
 **/
@RestController
public class SmsSqlOptimizeController {
    @Resource(name = "smsSqlOptimizeService")
    private SmsSqlOptimizeService smsSqlOptimizeService;

    @GetMapping("buildSmsTest/{times}/{appCode}/{smsMark}")
    @ApiOperation(value = "测试使用(多线程)，构造短信（10000条）")
    public ResultVO buildSmsTest(@PathVariable("times") int times,
                                 @PathVariable("appCode") String appCode,
                                 @PathVariable("smsMark") String smsMark){
        return smsSqlOptimizeService.buildSmsTest(times, appCode, smsMark);
    }

    @GetMapping("buildSms/{times}/{appCode}/{smsMark}")
    @ApiOperation(value = "测试使用(多线程)，构造短信（10000条）")
    public ResultVO buildSms(@PathVariable("times") int times,
                             @PathVariable("appCode") String appCode,
                             @PathVariable("smsMark") String smsMark){
        return smsSqlOptimizeService.buildSms(times, appCode, smsMark);
    }
}
