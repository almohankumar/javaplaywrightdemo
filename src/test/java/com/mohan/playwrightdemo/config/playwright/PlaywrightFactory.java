package com.mohan.playwrightdemo.config.playwright;

import com.beust.ah.A;
import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

public final class PlaywrightFactory {

    private PlaywrightFactory(){}

    // UI
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    //API
    private static ThreadLocal<APIRequestContext> apiRequestContext = new ThreadLocal<>();

    private static List<Playwright> activePlaywrights = new ArrayList<>();
    private static List<Browser> activeBrowsers = new ArrayList<>();
    private static List<BrowserContext>activeBrowserContexts = new ArrayList<>();

    static{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            activeBrowserContexts.forEach(BrowserContext::close);
            activeBrowsers.forEach(Browser::close);
            activePlaywrights.forEach(Playwright::close);

            System.out.println("Active Browser contexts, Browsers and Playwrights cleaned up and quitting gracefully!.");
        }));
    }

    public static Playwright getPlaywright(){
        return playwright.get();
    }

    public static void setPlaywright(Playwright playwright){
        PlaywrightFactory.playwright.set(playwright);
        activePlaywrights.add(playwright);
    }

    public static Browser getBrowser(){
        return browser.get();
    }

    public static void setBrowser(Browser browser){
        PlaywrightFactory.browser.set(browser);
        activeBrowsers.add(browser);
    }

    public static BrowserContext getBrowserContext(){
        return browserContext.get();
    }

    public static void setBrowserContext(BrowserContext browserContext){
        PlaywrightFactory.browserContext.set(browserContext);
        activeBrowserContexts.add(browserContext);
    }

    public static Page getPage(){
        return page.get();
    }

    public static void setUpPage(Page page){
        PlaywrightFactory.page.set(page);
    }

    public static void tearDownPage(){
        PlaywrightFactory.page.get().close();
    }

    public static void setUpRequestContext(APIRequestContext requestContext){
        PlaywrightFactory.apiRequestContext.set(requestContext);
    }

    public static void tearDownRequestContext(){
        PlaywrightFactory.apiRequestContext.get().dispose();
    }



}
