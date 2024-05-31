package com.mohan.playwrightdemo.runner;

import com.microsoft.playwright.Page;
import com.mohan.playwrightdemo.config.AppConfig;
import com.mohan.playwrightdemo.config.playwright.PlaywrightConfig;
import com.mohan.playwrightdemo.config.playwright.PlaywrightFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Hooks {


    @Autowired
    AppConfig appConfig;

    private final Map<String, PlaywrightConfig> playwrightConfigMap;

    @Autowired
    public Hooks(List<PlaywrightConfig> playwrightConfigs){
        playwrightConfigMap = playwrightConfigs
                .stream()
                .collect(Collectors.toMap(PlaywrightConfig::getName, Function.identity()));
    }

    @Before
    public void setUp(){
        PlaywrightConfig playwrightConfig = playwrightConfigMap.get(appConfig.getBrowser());
        playwrightConfig.startPlaywright();
    }


    @After
    public void tearDown(Scenario scenario){

        if(scenario.isFailed()){

            byte[] screenshot = PlaywrightFactory.getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        PlaywrightConfig playwrightConfig = playwrightConfigMap.get(appConfig.getBrowser());
        playwrightConfig.stopPlaywright();

    }
}
