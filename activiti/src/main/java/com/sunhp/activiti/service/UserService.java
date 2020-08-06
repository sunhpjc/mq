package com.sunhp.activiti.service;

import com.sunhp.activiti.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author sunhp
 * @since 2020-04-13
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    void insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 批量插入测试
     *
     */
    void insetMult(List<User> userList);

    /**
     * 批量更新测试
     * @param userList
     */
    void updateMult(List<User> userList);
}