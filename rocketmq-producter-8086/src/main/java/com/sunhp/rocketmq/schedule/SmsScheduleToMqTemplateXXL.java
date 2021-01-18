package com.sunhp.rocketmq.schedule;

import com.sunhp.rocketmq.dao.SmsDao;
import com.sunhp.rocketmq.entity.Sms;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/18 15:04
 **/
@Component
public class SmsScheduleToMqTemplateXXL {
    private static final Logger logger = LoggerFactory.getLogger(SmsScheduleToMqTemplateXXL.class);

    @Resource
    private SmsDao smsDao;

    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler(String param) throws Exception {

        // 分片参数
        int shardIndex = XxlJobContext.getXxlJobContext().getShardIndex();
        int shardTotal = XxlJobContext.getXxlJobContext().getShardTotal();

        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                List<Sms> smsList = smsDao.queryAllXXLJob(shardTotal, shardIndex);
                logger.info("================取数据===============");
                smsList.forEach(sms -> {
                    smsDao.updateWithStatus(sms, 1);
                    System.out.println(sms.toString());
                });
                XxlJobLogger.log("第 {} 片, 命中分片开始处理,{}", i,smsList.toString());
            } else {
                XxlJobLogger.log("第 {} 片, 忽略", i);
            }
        }

        return ReturnT.SUCCESS;
    }
}
