package org.lambdaTestCertification;

import com.microsoft.playwright.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.lambdaTestCertification.pages.DragAndDropPage;
import org.lambdaTestCertification.pages.InputFormPage;
import org.lambdaTestCertification.pages.PlaygroundPage;
import org.lambdaTestCertification.pages.utils.LambdaTestAPI;
import org.lambdaTestCertification.pages.utils.PlayWrightThread;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Log4j2
public class TestBase {
    //Playwright
    protected Page page;

    //Pages
    protected PlaygroundPage playgroundPage;
    protected DragAndDropPage dragAndDropPage;
    protected InputFormPage inputFormPage;

    //TestNG
    protected ThreadLocal<String> methodName = new ThreadLocal<>();

    @BeforeMethod
    void beforeMethod(ITestContext context, Method method) {
        log.warn("********************************************");
        String threadName = Thread.currentThread().getName();
        ThreadContext.put("threadName", threadName);
        log.info("This is a log message from thread: {}", threadName);
        methodName.set(method.getName());
        log.warn("{} is started", methodName.get());
        if (Boolean.parseBoolean(System.getProperty("isLambdaTest"))) {
            List<String> browsers = Arrays.asList("Chrome", "MicrosoftEdge", "pw-chromium", "pw-firefox", "pw-webkit");
            int randomIndex = new Random().nextInt(browsers.size());
            System.setProperty("browserName", browsers.get(randomIndex));
            log.info("Browser {} was randomly selected", System.getProperty("browserName"));
        }
        page = PlayWrightThread.getPage(System.getProperty("browserName"));
        String[] runInfo = LambdaTestAPI.getRunInfo(PlayWrightThread.playwrightThreadLocal.get());
        log.info("Report link: https://automation.lambdatest.com/test?build={}&selectedTab=home&testID={}", runInfo[1], runInfo[0]);
    }

    @AfterMethod
    void afterMethod(ITestResult result) {
        String status = result.isSuccess() ? "passed" : "failed";
        LambdaTestAPI.setTestStatus(status, methodName.get() + " " + status, page);
        log.warn("{} is finished with status {}", methodName.get(), status);
        PlayWrightThread.closePage(methodName.get());
        ThreadContext.clearAll();
    }
}
