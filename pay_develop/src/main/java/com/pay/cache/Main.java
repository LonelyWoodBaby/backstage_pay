package com.pay.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class Main {
    public static void main(String[] args) {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();

        Cache<Long,String> preConfigured = cacheManager.getCache("preConfigured",Long.class,String.class);
        Cache<Integer, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(10)).build());

        for(int i=0;i <= 20;i++){
            myCache.put(i,"@" + i);
            String value = myCache.get(i);
            System.out.println("get at " + i +":" +value);
        }
        cacheManager.removeCache("preConfigured");
        cacheManager.close();
    }
}
