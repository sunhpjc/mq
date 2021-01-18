package com.sunhp.rocketmq.schedule;

import com.alibaba.fastjson.JSONObject;
import com.sunhp.rocketmq.config.RocketMqConfig;
import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.service.SmsService;
import com.sunhp.rocketmq.utils.RedisUtil;
import com.sunhp.rocketmq.utils.RocketMqUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/12 11:49
 **/
@EnableScheduling
@Component
public class SmsScheduleToMq {
    private static final Logger logger = LoggerFactory.getLogger(SmsScheduleToMq.class);

    private static final String LOCK = "smsLock";
    private static final String TAGE_SMS = "tagSms";//rocket标签
    private static final int LIMIT = 10;//取数据限制
    private static final int STATUS_0 = 0;//发送状态为0
    private static final int STATUS_2 = 2;//发送状态为2
    private static final Long TIMEOUT = 60000L;//redis锁超时时间



    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SmsService smsService;
    @Resource
    private RocketMqUtil rocketMqUtil;
    @Resource
    private RocketMqConfig config;

//    @Scheduled(cron = "0/5 * * * * ?")
    public void smsScheduleToMq(){
        if(!redisUtil.exists(LOCK)){
            String identifier = redisUtil.addLockMq(LOCK,TIMEOUT);
            if(StringUtils.isNotBlank(identifier)){
                List<Sms> smsList = smsService.queryAllByLimit(0, LIMIT, STATUS_0);
                if(smsList != null && smsList.size() > 0){
                    smsList.forEach(sms -> {
                        String jsonString = JSONObject.toJSONString(sms);
                        String keys = String.valueOf(sms.getId());
                        try {
                            SendResult sendResult = rocketMqUtil.SyncProducer(jsonString, keys, TAGE_SMS, config.getTopic());
                            switch (sendResult.getSendStatus()){
                                case SEND_OK:
                                    smsService.updateBatch(smsList,STATUS_2);
                                    break;
                                default:
                                    logger.info("Timed task failed to produce messages,{}", JSONObject.toJSONString(sendResult));
                                    break;
                            }
                        } catch (Exception e) {
                            logger.error("Timed task failed to produce messages,{}",e);
                        }
                    });
                }
                else {
                    logger.info("There are no pending SMS messages in the database");
                }
                //解锁
                redisUtil.releaseLockMq(LOCK,identifier);
            }
        }else {
            logger.info("LOCK already exists");
        }
    }
}
