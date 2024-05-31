package com.mohan.playwrightdemo.controller;


import com.mohan.playwrightdemo.config.AppConfig;
import com.mohan.playwrightdemo.config.cache.CacheFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenController {

    @Autowired
    AppConfig appConfig;

    public String generateToken(){

        String token = null;
        String key = "authToken";
        token = CacheFactory.getValue(key);

        if(token == null){
            log.info("Token URL: "+appConfig.getTokenUrl());
            log.info("Token not found in cache, Generating and adding to cache!");

            token = "my20minutestoken";
            CacheFactory.putValue(key,token);

        }
        return token;

    }
}
