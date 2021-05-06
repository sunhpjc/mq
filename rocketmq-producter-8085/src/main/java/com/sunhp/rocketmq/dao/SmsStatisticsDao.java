package com.sunhp.rocketmq.dao;

import com.sunhp.rocketmq.entity.SmsStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SmsStatistics)表数据库访问层
 *
 * @author sunhp
 * @since 2021-04-20 11:38:48
 */
@Mapper
public interface SmsStatisticsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SmsStatistics queryById(Object id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SmsStatistics> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param smsStatistics 实例对象
     * @return 对象列表
     */
    List<SmsStatistics> queryAll(SmsStatistics smsStatistics);

    /**
     * 修改数据
     *
     * @param smsStatistics 实例对象
     * @return 影响行数
     */
    int update1(SmsStatistics smsStatistics);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);


    /////////////////

    /**
     * 获取上一条数据统计的最后一个id
     * @return
     */
    SmsStatistics getPreviousId();

    /**
     * 新增数据
     *
     * @param smsStatistics 实例对象
     * @return 影响行数
     */
    int insert(SmsStatistics smsStatistics);

    /**
     * 更新数据
     * @param smsStatistics
     * @return
     */
    int update(SmsStatistics smsStatistics);

    /**
     * 根据时间更新数据
     */
    int updateByTime(SmsStatistics smsStatistics);
}