package com.mohan.playwrightdemo.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@Getter
@Scope("singleton")
@PropertySource({"classpath:${spring.profiles.active}.playwrightdemo.properties","classpath:application.properties"})
public class AppConfig {

    @Value("${token.url}")
    private String tokenUrl;

    @Value("${browser}")
    private String browser;

    @Value("${browser.mode}")
    private String browserMode;

    @Value("${playwright.viewport.width}")
    private int width;

    @Value("${playwright.viewport.height}")
    private int height;


}
