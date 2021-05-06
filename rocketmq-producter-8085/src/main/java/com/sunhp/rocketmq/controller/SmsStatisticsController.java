package com.sunhp.rocketmq.controller;

import com.sunhp.rocketmq.entity.SmsStatistics;
import com.sunhp.rocketmq.service.SmsStatisticsService;
import com.sunhp.rocketmq.vo.response.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SmsStatistics)表控制层
 *
 * @author sunhp
 * @since 2021-04-20 11:38:48
 */
@RestController
@RequestMapping("smsStatistics")
public class SmsStatisticsController {
    /**
     * 服务对象
     */
    @Resource
    private SmsStatisticsService smsStatisticsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SmsStatistics selectOne(Long id) {
        return this.smsStatisticsService.queryById(id);
    }

    @GetMapping("/get/count")
    public ResultVO getCount(){
        return smsStatisticsService.getCount();
    }

    /**
     *
     * @return
     */
    @GetMapping("/get/count2")
    public ResultVO getCount2(){
        return smsStatisticsService.getCount2();
    }
}