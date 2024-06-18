package org.lambdaTestCertification.pages;

import com.microsoft.playwright.Page;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlaygroundPage extends AbstractPage {
    private final Page page;

    private final String simpleFormDemoBtn = "//a[contains(text(), 'Simple Form Demo')]";
    private final String enterMessageInputField = "//input[@id = 'user-message']";
    private final String getCheckedValueBtn = "//button[@id = 'showInput']";
    private final String yourMessageText = "//p[@id = 'message']";

    public PlaygroundPage(Page page) {
        this.page = page;
//        page.onConsoleMessage(message -> {
//            System.out.println("Console message: " + message.text());
//        });
        loadPage(page,"https://www.lambdatest.com/selenium-playground");
        openSimpleFormDemoPage();
    }

    public void openSimpleFormDemoPage() {
        page.click(simpleFormDemoBtn);
        log.info("Simple demo page is opened");
    }

    public String getPageUrl() {
        String pageURL = page.url();
        log.info("Current URL is {}", pageURL);
        return pageURL;
    }

    public void inputMessage(String value) {
        page.fill(enterMessageInputField, value);
        log.info("Inputting text: {}", value);
    }

    public String getYourMessage() {
        page.click(getCheckedValueBtn);
        log.info("Clicked on get checked value button");
        takeScreenShot(page, "yourMessage");
        return page.textContent(yourMessageText);
    }
}