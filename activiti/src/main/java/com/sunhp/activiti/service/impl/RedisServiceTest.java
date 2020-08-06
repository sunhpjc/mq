package com.sunhp.activiti.service.impl;

import com.sunhp.activiti.dao.UserDao;
import com.sunhp.activiti.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunhp
 * @Description 用于Redis雪崩解决方案中加锁方案
 * @Date 2020/4/20 10:01
 **/
@Service
public class RedisServiceTest {
    @Resource
    private UserDao userDao;
    @Resource
    private RedisService redisService;

    private Lock lock = new ReentrantLock();

    public String getUser(Integer id){
        //定义key，key以当前的类名+方法名+id+参数值
        String key = this.getClass().getName()+"-"+Thread.currentThread().getStackTrace()[1].getMethodName()+"-id:"+id;
        //查询redis
        String username = redisService.getString(key);
        if(StringUtils.isNotEmpty(username)){
            return username;
        }
        String resultUserName = null;

        try {
            //开启锁
            lock.lock();
            User user = userDao.queryById(id);
            if(user.getUsername()==null){
                return null;
            }
            resultUserName = user.getUsername();
            redisService.setString(key,resultUserName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return resultUserName;
    }
}
