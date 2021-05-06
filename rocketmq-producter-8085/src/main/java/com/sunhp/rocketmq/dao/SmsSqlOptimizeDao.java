package com.sunhp.rocketmq.dao;

import com.sunhp.rocketmq.entity.SendSms;
import com.sunhp.rocketmq.vo.request.CountReq;
import com.sunhp.rocketmq.vo.response.CountTotalResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/19 15:39
 **/
@Mapper
public interface SmsSqlOptimizeDao {
    /**
     * 批量新增数据
     * @param smsList
     */
    void insertBatch(List<SendSms> smsList);

    /**
     * 查询成功总数
     * @return
     */
    Long selectCountSuccess(CountReq countReq);

    /**
     * 查询所有总数
     * @return
     */
    CountTotalResp selectCountTotal(CountReq countReq);
}
