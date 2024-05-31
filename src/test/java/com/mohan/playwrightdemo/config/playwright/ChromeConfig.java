package com.mohan.playwrightdemo.config.playwright;

import com.microsoft.playwright.*;
import com.mohan.playwrightdemo.annotations.LazyAutowired;
import com.mohan.playwrightdemo.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Qualifier("chrome")
@Component
public class ChromeConfig implements PlaywrightConfig{

    @LazyAutowired
    AppConfig appConfig;

    @Override
    public void startPlaywright() {

        Playwright playwright = Playwright.create();
        PlaywrightFactory.setPlaywright(playwright);
        log.info("Playwright created on "+Thread.currentThread().getName());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(!appConfig.getBrowserMode().equalsIgnoreCase("headed"));

        Browser browser = PlaywrightFactory.getPlaywright().chromium().launch(launchOptions);
        PlaywrightFactory.setBrowser(browser);
        log.info("Chrome browser created on "+ Thread.currentThread().getName());

        BrowserContext browserContext = PlaywrightFactory.getBrowser()
                .newContext(new Browser.NewContextOptions().setViewportSize(appConfig.getWidth(),appConfig.getHeight()));
        browserContext.clearCookies();
        PlaywrightFactory.setBrowserContext(browserContext);
        log.info("Browser context created on "+ Thread.currentThread().getName());

        Page page = PlaywrightFactory.getBrowserContext().newPage();
        log.info("Page created on "+ Thread.currentThread().getName());
        PlaywrightFactory.setUpPage(page);

        APIRequestContext requestContext = playwright
                .request()
                .newContext(new APIRequest.NewContextOptions().setIgnoreHTTPSErrors(true));

        PlaywrightFactory.setUpRequestContext(requestContext);
        log.info("API request context created on "+ Thread.currentThread().getName());


        }

    @Override
    public void stopPlaywright() {

        PlaywrightFactory.tearDownPage();
        PlaywrightFactory.tearDownRequestContext();

    }

    @Override
    public String getName() {
        return "chrome";
    }



}
