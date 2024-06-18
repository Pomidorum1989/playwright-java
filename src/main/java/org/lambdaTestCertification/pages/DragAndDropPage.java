package org.lambdaTestCertification.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DragAndDropPage extends AbstractPage {
    private final Page page;

    private final String dragAndDropSlidersBtn = "Drag & Drop Sliders";
    private final String dragAndDropValue = "//output[@id='rangeSuccess']";

    public DragAndDropPage(Page page) {
        this.page = page;
        loadPage(page,"https://www.lambdatest.com/selenium-playground");
        openDragAndDropPage();
    }

    public void openDragAndDropPage() {
        clickByText(page, dragAndDropSlidersBtn);
    }

    public void moveSlider(int value) {
        page.locator("#slider3").getByRole(AriaRole.SLIDER).fill(String.valueOf(value));
        log.info("Moved slider to {}", value);
    }

    public int getDragAndDropValue() {
        int value = Integer.parseInt(page.locator(dragAndDropValue).textContent());
        log.info("Current drag&drop value is {}", value);
        takeScreenShot(page, "dragAndDropValue");
        return value;
    }
}
