package com.sunhp.drools.mapper;

import com.sunhp.drools.entity.Sms;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/27 16:34
 **/
@Mapper
public interface SmsMapper extends ReactiveCrudRepository<Sms,Long> {
    /**
     * 根据id查短信
     * @param id
     * @return
     */
    Mono<Sms> queryById(Long id);
}
