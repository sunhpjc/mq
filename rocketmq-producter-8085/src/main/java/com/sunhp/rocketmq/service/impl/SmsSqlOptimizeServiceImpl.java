package com.sunhp.rocketmq.service.impl;

import com.sunhp.rocketmq.dao.SmsSqlOptimizeDao;
import com.sunhp.rocketmq.entity.SendSms;
import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.service.SmsSqlOptimizeService;
import com.sunhp.rocketmq.utils.ListUtil;
import com.sunhp.rocketmq.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static ch.qos.logback.core.CoreConstants.CORE_POOL_SIZE;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/19 15:36
 **/
@Service(value = "smsSqlOptimizeService")
@Slf4j
public class SmsSqlOptimizeServiceImpl implements SmsSqlOptimizeService {
    @Resource
    private SmsSqlOptimizeDao smsSqlOptimizeDao;

    /**
     * 测试多线程插入数据
     *
     * @return
     */
    @Override
    public ResultVO buildSmsTest(int times, String appCode, String smsMark) {
        ResultVO resultVO = new ResultVO(ResponseCodeEnum.UNEXPECTED_EXCEPTION);
        long startTime = System.currentTimeMillis();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                5,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < times; i++) {
            List<SendSms> sendSmsList = getSms(appCode, smsMark);
            List<List<SendSms>> lists = ListUtil.listSplit(sendSmsList, 2000, 2000);

            for (List<SendSms> param : lists){
                executor.execute(()->{
                    try {
                        smsSqlOptimizeDao.insertBatch(param);
                    } catch (Exception e) {
                        log.error("插入数据失败==>{}，异常原因==>{}", param.toString(), e);
                    }
                });
            }
        }
        log.info("多线程耗时：{}ms",System.currentTimeMillis() - startTime);
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 插入数据
     *
     * @param times
     * @param appCode
     * @return
     */
    @Override
    public ResultVO buildSms(int times, String appCode, String smsMark) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < times; i++) {
            List<SendSms> sendSmsList = getSms(appCode, smsMark);
            List<List<SendSms>> lists = ListUtil.listSplit(sendSmsList, 2000, 2000);

            for (List<SendSms> param : lists){
                try {
                    smsSqlOptimizeDao.insertBatch(param);
                } catch (Exception e) {
                    log.error("插入数据失败==>{}，异常原因==>{}", param.toString(), e);
                }
            }
        }
        log.info("非多线程耗时：{}ms",System.currentTimeMillis() - startTime);
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    private List<SendSms> getSms(String appCode,String smsMark){
        List<SendSms> smsList = new ArrayList<>();
        String batchNo = UUID.randomUUID().toString();
        for (int i = 0; i < 10000; i++) {
            SendSms sendSms = new SendSms();
            sendSms.setBatchNo(batchNo);
            sendSms.setAppCode(appCode);
            sendSms.setTemplateId("templateId");
            sendSms.setTargetPhone("18782689"+i);
            sendSms.setSmsContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
            sendSms.setSmsMark(smsMark);
            sendSms.setCreateUser("创建人");
            sendSms.setUpdateUser("更新人");
            smsList.add(sendSms);
        }
        return smsList;
    }
}
