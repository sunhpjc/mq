package com.sunhp.activiti.we_chat_inter.guava.intf.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sunhp.activiti.dao.UserDao;
import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.we_chat_inter.guava.intf.GuavaLocalCacheLoading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 用户缓存
 * @Date 2022/10/20 16:54
 **/
@Service("userCache")
@Slf4j
public class UserCacheLoadingLoading implements GuavaLocalCacheLoading<Integer, User> {
    protected final UserDao userDao;

    public UserCacheLoadingLoading(UserDao userDao) {this.userDao = userDao;}

    @Override
    public LoadingCache<Integer, User> cacheLoading() throws MyException{
        return userCache;
    }

    @Override
    public User getDataWhenLoadingFail(Integer param) {
        //后面考虑两级缓存（根据场景来确定）
        return userDao.queryById(param);
    }

    private LoadingCache<Integer, User> userCache = CacheBuilder.newBuilder()
        //设置缓存初始大小，应该合理设置，后续会扩容
        .initialCapacity(10)
        //最大值
        .maximumSize(100)
        //并发数设置
        .concurrencyLevel(5)
        //缓存过期时间，写入后15s过期
        .expireAfterWrite(15, TimeUnit.SECONDS)
        // 此缓存对象经过多少10秒没有被访问则过期。
        .expireAfterAccess(10, TimeUnit.SECONDS)
        //统计缓存命中率
        .recordStats()
        .build(new CacheLoader<Integer, User>() {
            @Override
            public User load(Integer id) throws MyException{
                User user = userDao.queryById(id);
                log.info("数据库获取user:{}", JSONObject.toJSONString(user));
                return Optional.ofNullable(user)
                    .orElseThrow(() -> new MyException(id + "该用户不存在"));
            }
        });
}
