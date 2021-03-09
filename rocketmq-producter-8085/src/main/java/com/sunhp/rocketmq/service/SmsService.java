package com.sunhp.rocketmq.service;

import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.vo.response.ResultVO;
import com.sunhp.rocketmq.vo.response.SmsBack;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

/**
 * (Sms)表服务接口
 *
 * @author makejava
 * @since 2020-12-31 11:27:56
 */
public interface SmsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Sms queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Sms> queryAllByLimit(int offset, int limit, int status);

    /**
     * 流式查询数据
     * @param limit
     * @return
     */
    ResultVO smsCursor(int limit);

    /**
     * 新增数据
     *
     * @param sms 实例对象
     * @return 实例对象
     */
    Sms insert(Sms sms);

    ResultVO insertBatch(List<Sms> smsList);

    /**
     * 测试构造数据
     * @return
     */
    ResultVO buildSms();

    /**
     * 测试多线程插入数据
     * @return
     */
    ResultVO buildSmsTest();

    /**
     * 修改数据
     *
     * @param sms 实例对象
     * @return 实例对象
     */
    Sms update(Sms sms);

    /**
     * 更新数据
     * @param sms
     * @param status
     * @return
     */
    Sms update(Sms sms, int status);

    /**
     * 批量修改数据
     * @param smsList
     * @param status
     * @return
     */
    ResultVO updateBatch(List<Sms> smsList, int status);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Object id);

    /**
     * smsListBack和smsListBack1比较内存消耗
     * @return
     */
    List<SmsBack> smsListBack();

    /**
     * smsListBack和smsListBack1比较内存消耗
     * @return
     */
    List<Sms> smsListBack1();

}