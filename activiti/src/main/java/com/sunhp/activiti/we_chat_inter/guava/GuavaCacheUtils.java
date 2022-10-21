package com.sunhp.activiti.we_chat_inter.guava;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.sunhp.activiti.we_chat_inter.guava.enums.CacheTypeEnums;
import com.sunhp.activiti.we_chat_inter.guava.intf.GuavaLocalCacheLoading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 缓存工具类
 * @Date 2022/10/21 11:05
 **/
@Component
@Slf4j
public class GuavaCacheUtils<K, V> {
    @Autowired
    private final Map<String, GuavaLocalCacheLoading> map = Maps.newConcurrentMap();

    /**
     * 获取缓存
     *
     * @param enums
     * @param param
     * @return
     */
    public V getCache(CacheTypeEnums enums, K param) {
        GuavaLocalCacheLoading<K, V> loading = Optional.ofNullable(map.get(enums.getCode()))
            .get();
        if (null != loading) {
            LoadingCache<K, V> loadingCache = loading.cacheLoading();
            V v = null;
            try {
                v = loadingCache.get(param);
            } catch (ExecutionException e) {
                log.info("guava缓存获取异常:{}", e);
            }
            if (null != v) {
                return v;
            }
            return loading.getDataWhenLoadingFail(param);
        }
        return null;
    }

}
