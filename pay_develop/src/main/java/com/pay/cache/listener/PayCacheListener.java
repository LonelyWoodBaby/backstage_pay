package com.pay.cache.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayCacheListener implements CacheEventListener<Object,Object>{
    private Logger logger = LoggerFactory.getLogger(PayCacheListener.class);
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        logger.info("Event: " + cacheEvent.getType() +
        ",key: " + cacheEvent.getKey() +
        ", old Value : " + cacheEvent.getOldValue() +
        ", new Value : " + cacheEvent.getNewValue());
    }
}
