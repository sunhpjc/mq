package com.sunhp.drools.servic.impl;

import com.sunhp.drools.entity.Sms;
import com.sunhp.drools.mapper.SmsMapper;
import com.sunhp.drools.servic.SmsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/27 16:57
 **/
@Service(value = "smsService")
public class SmsServiceImpl implements SmsService {
    @Resource
    SmsMapper smsMapper;

    /**
     * 根据id查询短信
     *
     * @param id
     * @return
     */
    @Override
    public Mono<Sms> queryById(Long id) {
        return smsMapper.queryById(id);
    }
}
