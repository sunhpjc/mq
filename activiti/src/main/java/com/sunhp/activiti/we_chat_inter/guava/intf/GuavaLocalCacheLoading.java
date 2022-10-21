package com.sunhp.activiti.we_chat_inter.guava.intf;

import com.google.common.cache.LoadingCache;

/**
 * @author sunhp
 * @Description 缓存通用接口
 * @Date 2022/10/20 16:48
 **/
public interface GuavaLocalCacheLoading<K,V> {
    /**
     * 缓存加载
     * @return
     */
    LoadingCache<K,V> cacheLoading();

    /**
     * 自动加载失败通过sql查询获取数据
     * @param param
     * @return
     */
    V getDataWhenLoadingFail(K param);
}
