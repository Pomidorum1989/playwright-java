package org.lambdaTestCertification.pages.utils;

import com.microsoft.playwright.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;

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
        BrowserType browserType = getBrowserType(playwright, browserName);
        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        newContextOptions.acceptDownloads = true;

        BrowserContext context = browser.newContext(newContextOptions);
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        browserTypeThreadLocal.set(browserType);
        browserThreadLocal.set(browser);
        browserContextThreadLocal.set(context);

        return context.newPage();

    }

    private static BrowserType getBrowserType(Playwright playwright, String browserName) {
        switch (browserName) {
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

    public static synchronized void closePage(Method method) {
        Playwright playwright = playwrightThreadLocal.get();
        Page page = pageThreadLocal.get();
        BrowserContext browserContext = browserContextThreadLocal.get();
        if (playwright != null) {
            browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("target/trace/" + method.getName() + "_trace.zip")));
            browserContext.close();
            browserContextThreadLocal.remove();
            page.close();
            pageThreadLocal.remove();
            playwright.close();
            playwrightThreadLocal.remove();
        }
    }
}