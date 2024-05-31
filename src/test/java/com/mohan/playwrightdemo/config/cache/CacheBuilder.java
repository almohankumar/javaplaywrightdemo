package com.mohan.playwrightdemo.config.cache;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheBuilder{

    Cache<String,String> cache = null;

    public Cache<String,String> buildCache(String cacheName, int lifeSpanInMinutes){

            DefaultCacheManager cacheManager = new DefaultCacheManager();
            cacheManager.defineConfiguration(cacheName,setExpiringConfiguration(lifeSpanInMinutes));
            Cache<String,String> cache =  cacheManager.getCache(cacheName);
            cache.addListener(new CacheListener());
            return cache;




    }

    private Configuration setExpiringConfiguration (int lifeSpanInMinutes){

        return new ConfigurationBuilder().expiration()
                .lifespan(lifeSpanInMinutes, TimeUnit.MINUTES)
                .build();
    }


}
