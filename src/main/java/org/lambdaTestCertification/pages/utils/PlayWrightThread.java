package org.lambdaTestCertification.pages.utils;

import com.microsoft.playwright.*;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.nio.file.Paths;

@Log4j2
public class PlayWrightThread {

    public static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<BrowserType> browserTypeThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static synchronized Page getPage(String browserName) {
        if (playwrightThreadLocal.get() == null) {
            Playwright playwright = Playwright.create();
            playwrightThreadLocal.set(playwright);
            Page page = createPage(playwright, browserName);
            pageThreadLocal.set(page);
        }
        return pageThreadLocal.get();
    }

    private static synchronized Page createPage(Playwright playwright, String browserName) {
        Browser browser;
        BrowserType browserType;
        if (Boolean.parseBoolean(System.getProperty("isLambdaTest"))) {
            String url = LambdaTestCapabilities.CDP_URL + LambdaTestCapabilities.generateCapabilities(browserName);
            log.info("Connecting to {}", url);
            browserType = getBrowserType(playwright, "chromium");
            browser = browserType.connect(url);
            log.info("Running browser {} on lambda test cloud", browserName);
        } else {
            boolean isHeadless = Boolean.parseBoolean(System.getProperty("isHeadless"));
            browserType = getBrowserType(playwright, browserName);
            browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
            log.info("Running browser {} in headless: {}", browserName, isHeadless);
        }
        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        newContextOptions.acceptDownloads = true;
        BrowserContext context = browser.newContext(newContextOptions);
        browserTypeThreadLocal.set(browserType);
        browserThreadLocal.set(browser);
        browserContextThreadLocal.set(context);
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
        return context.newPage();
    }

    private static BrowserType getBrowserType(Playwright playwright, String browserName) {
        switch (browserName.toLowerCase()) {
            case "chromium":
                return playwright.chromium();
            case "webkit":
                return playwright.webkit();
            case "firefox":
                return playwright.firefox();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static synchronized void closePage(String methodName) {
        Playwright playwright = playwrightThreadLocal.get();
        Page page = pageThreadLocal.get();
        BrowserContext browserContext = browserContextThreadLocal.get();
        if (playwright != null) {
            String fileName = "target/trace/" + methodName + "_trace.zip";
            browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(fileName)));
            browserContext.close();
            log.info("Trace file was created {}", fileName);
            browserContextThreadLocal.remove();
            page.close();
            pageThreadLocal.remove();
            playwright.close();
            playwrightThreadLocal.remove();
        }
    }
}