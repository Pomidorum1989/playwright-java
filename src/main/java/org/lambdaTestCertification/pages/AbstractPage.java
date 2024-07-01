package org.lambdaTestCertification.pages;

import com.microsoft.playwright.Page;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Paths;

@Log4j2
public abstract class AbstractPage {

    public AbstractPage() {
    }

    //Private methods
    protected void clickByText(Page page, String text) {
        page.getByText(text).click();
        log.info("Clicked on {}", text);
    }

    protected void inputByPlaceHolder(Page page, String placeHolder, String text) {
        page.getByPlaceholder(placeHolder, new Page.GetByPlaceholderOptions().setExact(true)).fill(text);
        log.info("Add text {} to {} filed", text, placeHolder);
    }

    protected void loadPage(Page page, String url) {
        page.navigate(url);
    }

    protected void takeScreenShot(Page page, String screenShotName) {
        String fileName = "target/screenshots/" + screenShotName + ".png";
        page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(fileName)));
        log.info("Screenshot {} was created", fileName);
    }
}
