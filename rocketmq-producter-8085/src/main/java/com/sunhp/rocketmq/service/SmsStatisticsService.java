package com.sunhp.rocketmq.service;

import com.sunhp.rocketmq.entity.SmsStatistics;
import com.sunhp.rocketmq.vo.response.ResultVO;

import java.util.List;

/**
 * (SmsStatistics)表服务接口
 *
 * @author sunhp
 * @since 2021-04-20 11:38:48
 */
public interface SmsStatisticsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SmsStatistics queryById(Object id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SmsStatistics> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param smsStatistics 实例对象
     * @return 实例对象
     */
    SmsStatistics insert(SmsStatistics smsStatistics);

    /**
     * 修改数据
     *
     * @param smsStatistics 实例对象
     * @return 实例对象
     */
    SmsStatistics update(SmsStatistics smsStatistics);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Object id);

    /**
     * 获取每小时数据
     * @return
     */
    ResultVO getCount();

    /**
     * 获取每小时数据
     * @return
     */
    ResultVO getCount1();
    ResultVO getCount2();

}