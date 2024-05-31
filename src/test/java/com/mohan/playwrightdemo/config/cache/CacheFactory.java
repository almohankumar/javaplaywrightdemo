package com.mohan.playwrightdemo.config.cache;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public final class CacheFactory {

    private static final ReentrantLock lock = new ReentrantLock();

    private CacheFactory(){}

    private static Cache<String,String> cache =null;

    static{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            clearCache();

            System.out.println("Cache cleaned up..");
        }));
    }

    public static void setCache(Cache<String,String> cache){

        lock.lock();

        try{
            if(CacheFactory.cache==null){
                log.info("Initializing Cache...");
                CacheFactory.cache = cache;
            }
        }finally {
            lock.unlock();
        }


    }

    public static void clearCache(){
        if (CacheFactory.cache!=null){
            CacheFactory.cache.clear();
        }
    }

    public static String getValue(String key){
        return cache.get(key);
    }

    public static void putValue(String key, String value){
        cache.putIfAbsent(key,value);
    }


}
