package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunhp.activiti.dao.UserDao;
import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelEnums;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.StrategyFactory;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.service.SmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/11/1 14:55
 **/
@RestController
@RequestMapping("smsTestAnnotation")
@Slf4j
public class AnnotationTestController {
    private final UserDao userDao;

    public AnnotationTestController(UserDao userDao) {this.userDao = userDao;}

    @GetMapping("/testAnnotation")
    public void testAnnotation(String channelCode) {
        ChannelEnums channelEnum = ChannelEnums.find(channelCode);
        Optional.ofNullable(channelEnum)
            .orElseThrow(() -> new MyException("不存在对应渠道"));
        SmsService smsService = StrategyFactory.getSmsServiceByChannelType(channelEnum);
        smsService.sendSmsBatch();
    }

    @GetMapping("/testAnnotationFor")
    public void testAnnotationFor() {
        List<String> stringList = new ArrayList<>();
        stringList.add("CHANNEL_ONE");
        stringList.add("qeewq");
        stringList.add("CHANNEL_TWO");
        stringList.add("CHANNEL_THREE");

        for (String str : stringList) {
            ChannelEnums channelEnum = ChannelEnums.find(str);
            if (null == channelEnum) {
                log.info("不支持当前渠道 渠道编号：{}", str);
                continue;
            }
            try {
                SmsService smsService = StrategyFactory.getSmsServiceByChannelType(channelEnum);
                switch (channelEnum) {
                    case CHANNEL_ONE:
                        smsService.sendSmsBatch();
                        break;
                    case CHANNEL_TWO:
                        smsService.sendSmsBatch();
                        break;
                    case CHANNEL_THREE:
                        smsService.smsBackStatus();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.info("渠道异常 渠道编号：{} 异常：{}",str, e);
            }
        }
    }

    @GetMapping("/testDebug")
    public void testDebug(){
        log.info("测试info日志");
        log.debug("测试debug日志");
        log.error("测试error日志");
    }

    @GetMapping("testSqlLog")
    public void getUser(int id){
        userDao.queryById(id);
    }

}
