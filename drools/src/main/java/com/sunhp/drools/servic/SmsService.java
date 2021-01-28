package com.sunhp.drools.servic;

import com.sunhp.drools.entity.Sms;
import reactor.core.publisher.Mono;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/27 16:55
 **/
public interface SmsService {
    /**
     * 根据id查询短信
     * @param id
     * @return
     */
    Mono<Sms> queryById(Long id);
}
