package com.mohan.playwrightdemo.runner;

import com.mohan.playwrightdemo.config.AppConfig;
import com.mohan.playwrightdemo.config.cache.CacheFactory;
import com.mohan.playwrightdemo.config.TestConfig;
import com.mohan.playwrightdemo.config.cache.CacheBuilder;
import com.mohan.playwrightdemo.controller.TokenController;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


@CucumberOptions(features = "src/test/resources/features",
        glue = {"com.mohan.playwrightdemo.steps","com.mohan.playwrightdemo"},
        tags = "@hello",
        plugin = {"json:target/jsonReports/cucumber.json"},
        dryRun = false,
        monochrome = true)

@Slf4j
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }

    @BeforeClass
    public void generateAndCacheToken(){

        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        CacheBuilder cacheBuilder = context.getBean(CacheBuilder.class);
        TokenController tokenController = context.getBean(TokenController.class);
        AppConfig appConfig = context.getBean(AppConfig.class);

        CacheFactory.setCache(cacheBuilder.buildCache("tokens",20));
        log.info("Generating and caching token!");
        tokenController.generateToken();

    }


}
