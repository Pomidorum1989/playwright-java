package org.lambdaTestCertification;

import com.microsoft.playwright.assertions.PageAssertions;
import org.lambdaTestCertification.pages.DragAndDropPage;
import org.lambdaTestCertification.pages.InputFormPage;
import org.lambdaTestCertification.pages.PlaygroundPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestScenarios extends TestBase {

    @Test(description = "Playground page test", priority = 1)
    public void testOne() {
        String inputText = "Welcome to LambdaTest";
        playgroundPage = new PlaygroundPage(page);
        assertThat(page).hasURL(playgroundPage.getPageUrl(), new PageAssertions.HasURLOptions().setIgnoreCase(true).setTimeout(5000));
        playgroundPage.inputMessage(inputText);
        Assert.assertEquals(playgroundPage.getYourMessage(), inputText, "Incorrect input text");
    }

    @Test(description = "Drag and drop page test", priority = 2)
    public void testTwo() {
        dragAndDropPage = new DragAndDropPage(page);
        dragAndDropPage.getDragAndDropValue();
        dragAndDropPage.moveSlider(95);
        Assert.assertEquals(dragAndDropPage.getDragAndDropValue(), 95, "Incorrect drag and drop value");
    }

    @Test(description = "Input form page test", priority = 3)
    public void testThree() {
        inputFormPage = new InputFormPage(page);
        inputFormPage.clickSubmitBtn();
        Assert.assertTrue(inputFormPage.isAttributeExist("required"), "Incorrect attribute");
        inputFormPage.fillName("Valentine");
        inputFormPage.fillEmail("pomidorum1989@gmail.com");
        inputFormPage.fillPassword("123");
        inputFormPage.fillCompany("LambdaTest");
        inputFormPage.fillWebSite("https://www.lambdatest.com/");
        inputFormPage.selectCountry("US");
        inputFormPage.selectCity("New York");
        inputFormPage.selectAddress1("20 W 34th Street");
        inputFormPage.selectAddress2("20 W 34th Street");
        inputFormPage.selectState("NY");
        inputFormPage.selectZipCode("10001");
        inputFormPage.clickSubmitBtn();
        Assert.assertTrue(inputFormPage.isSuccessMessageDisplayed(), "Success message didn't appear");
    }
}
