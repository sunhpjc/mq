package com.sunhp.rocketmq.dao;

import com.sunhp.rocketmq.entity.Sms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Sms)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-31 11:27:56
 */
@Mapper
public interface SmsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Sms queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Sms> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sms 实例对象
     * @return 对象列表
     */
    List<Sms> queryAll(Sms sms);

    /**
     * 新增数据
     *
     * @param sms 实例对象
     * @return 影响行数
     */
    int insert(Sms sms);

    void insertBatch(List<Sms> smsList);

    /**
     * 修改数据
     *
     * @param sms 实例对象
     * @return 影响行数
     */
    int update(Sms sms);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}