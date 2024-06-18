package org.lambdaTestCertification;

import com.microsoft.playwright.*;
import lombok.extern.log4j.Log4j2;
import org.lambdaTestCertification.pages.utils.PlayWrightThread;
import org.testng.annotations.*;
import org.lambdaTestCertification.pages.*;

import java.lang.reflect.Method;


@Log4j2
public class TestBase {
    //Playwright
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    //Pages
    PlaygroundPage playgroundPage;
    DragAndDropPage dragAndDropPage;
    InputFormPage inputFormPage;

    @BeforeMethod
    void createContextAndPage(Method method) {
        log.warn("{} is started", method.getName());
        page = PlayWrightThread.getPage("chromium");
    }

    @AfterMethod
    void closeContext(Method method) {
        log.warn("{} is finished", method.getName());
        PlayWrightThread.closePage(method);
    }
}
