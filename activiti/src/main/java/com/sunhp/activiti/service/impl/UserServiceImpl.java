package com.sunhp.activiti.service.impl;

import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.dao.UserDao;
import com.sunhp.activiti.service.UserService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author sunhp
 * @since 2020-04-13
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(User user){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // explicitly setting the transaction name is something that can only be done programmatically
        def.setName("insertUser");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // execute your business logic here 在这里编写业务逻辑
            //db operation 数据库操作
            this.userDao.insert(user);
            System.out.println(user);
            //throw new SQLException("数据库异常");
        } catch (Exception e) {
            e.printStackTrace();
            //手动回滚
            transactionManager.rollback(status);
        }
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    /**
     * 批量插入
     * @param userList
     */
    @Override
    public void insetMult(List<User> userList) {
        userDao.insertMult(userList);
    }

    /**
     * 批量更新测试
     * @param userList
     */
    @Override
    public void updateMult(List<User> userList) {
        userDao.updateMult(userList);
    }
}