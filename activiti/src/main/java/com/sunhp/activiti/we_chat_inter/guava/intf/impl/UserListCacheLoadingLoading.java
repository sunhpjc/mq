package com.sunhp.activiti.we_chat_inter.guava.intf.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sunhp.activiti.dao.UserDao;
import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.we_chat_inter.guava.intf.GuavaLocalCacheLoading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/20 17:05
 **/
@Service("userListCache")
@Slf4j
public class UserListCacheLoadingLoading implements GuavaLocalCacheLoading<String, List<User>> {
    private final UserDao userDao;

    public UserListCacheLoadingLoading(UserDao userDao) {this.userDao = userDao;}

    public LoadingCache<String, List<User>> getUserListCache() {
        return userListCache;
    }

    @Override
    public LoadingCache cacheLoading() {
        return userListCache;
    }

    @Override
    public List<User> getDataWhenLoadingFail(String param) {
        return userDao.queryAllByLimit(0,10);
    }

    private LoadingCache<String, List<User>> userListCache = CacheBuilder.newBuilder()
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
        .build(new CacheLoader<String, List<User>>() {
            @Override
            public List<User> load(String listKey) {
                List<User> userList = userDao.queryAllByLimit(0, 10);
                log.info("数据库获取user:{}", JSONObject.toJSONString(userList));
                return Optional.of(userList)
                    .orElse(null);
            }
        });
}
