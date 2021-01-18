package com.sunhp.rocketmq.schedule;

import com.alibaba.fastjson.JSONObject;
import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.service.SmsService;
import com.sunhp.rocketmq.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/14 16:15
 **/
@EnableScheduling
@Component
public class SmsScheduleToMqTemplate {
    private static final Logger logger = LoggerFactory.getLogger(SmsScheduleToMq.class);

    private static final String LOCK = "smsLock";
    private static final String TOPIC_SMS = "topicSms";//rocket主题
    private static final String TAGE_SMS = "tagSms";//rocket标签
    private static final int LIMIT = 1000;//取数据限制
    private static final int STATUS_0 = 0;//发送状态为0
    private static final int STATUS_2 = 2;//发送状态为2,发送中
    private static final Long TIMEOUT = 60000L;//redis锁超时时间



    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SmsService smsService;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

//    @Scheduled(cron = "0/5 * * * * ?")
    public void smsScheduleToMq(){
        if(!redisUtil.exists(LOCK)){
            String identifier = redisUtil.addLockMq(LOCK,TIMEOUT);
            if(StringUtils.isNotBlank(identifier)){
                List<Sms> smsList = smsService.queryAllByLimit(0, LIMIT, STATUS_0);
                if(smsList != null && smsList.size() > 0){
                    List<Sms> success = new ArrayList<>();
                    smsList.forEach(sms -> {
//                        String jsonString = JSONObject.toJSONString(sms);
                        String keys = String.valueOf(sms.getId());
                        Message message = MessageBuilder.withPayload(sms).setHeader("KEYS", keys).build();
                        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC_SMS+":"+TAGE_SMS, message,10000, 5);
                        SendStatus sendStatus = sendResult.getSendStatus();
                        switch (sendStatus){
                            case SEND_OK:
//                                批量操作
//                                success.add(sms);
                                smsService.update(sms, STATUS_2);
                                break;
                            default:
                                logger.info("Timed task failed to produce messages,{}", JSONObject.toJSONString(sendResult));
                                break;
                        }
                    });
//                    批量操作
//                    smsService.updateBatch(success, STATUS_2);
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
