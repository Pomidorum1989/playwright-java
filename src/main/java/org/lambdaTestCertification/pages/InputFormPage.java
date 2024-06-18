package org.lambdaTestCertification.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InputFormPage extends AbstractPage {
    private final Page page;

    private final String submitBtn = "Submit";
    private final String inputFormSubmitBtn = "Input Form Submit";
    private final String nameField = "Name";
    private final String emailField = "Email";
    private final String passwordField = "Password";
    private final String companyFiled = "Company";
    private final String cityField = "City";
    private final String websiteField = "Website";
    private final String address1Field = "Address 1";
    private final String address2Field = "Address 2";
    private final String stateField = "State";
    private final String zipCodeField = "Zip code";
    private final String successMessage = "Thanks for contacting us, we will get back to you shortly.";
    private final String successMessage1 = "//p[@class = 'success-msg hidden']";

    public InputFormPage(Page page) {
        this.page = page;
        loadPage(page, "https://www.lambdatest.com/selenium-playground");
        openInputSubmitForm();
    }

    public void openInputSubmitForm() {
        clickByText(page, inputFormSubmitBtn);
    }

    public void clickSubmitBtn() {
        clickByText(page, submitBtn);
    }

    public boolean isAttributeExist(String attributeName) {
        String attribute = page.getByPlaceholder(nameField, new Page.GetByPlaceholderOptions().setExact(true)).getAttribute(attributeName);
        return attribute != null;
    }

    public void fillName(String text) {
        inputByPlaceHolder(page, nameField, text);
    }

    public void fillEmail(String text) {
        inputByPlaceHolder(page, emailField, text);
    }

    public void fillPassword(String text) {
        inputByPlaceHolder(page, passwordField, text);
    }

    public void fillCompany(String text) {
        inputByPlaceHolder(page, companyFiled, text);
    }

    public void fillWebSite(String text) {
        inputByPlaceHolder(page, websiteField, text);
    }

    public void selectCountry(String name) {
        page.getByRole(AriaRole.COMBOBOX).selectOption(name);
        log.info("Selected {} country", name);
    }

    public void selectCity(String name) {
        inputByPlaceHolder(page, cityField, name);
    }

    public void selectAddress1(String name) {
        inputByPlaceHolder(page, address1Field, name);
    }

    public void selectAddress2(String name) {
        inputByPlaceHolder(page, address2Field, name);
    }

    public void selectState(String name) {
        inputByPlaceHolder(page, stateField, name);
    }

    public void selectZipCode(String name) {
        inputByPlaceHolder(page, zipCodeField, name);
    }

    public boolean isSuccessMessageDisplayed() {
//        page.locator(successMessage1).highlight();
        takeScreenShot(page, "successMessage");
        return page.getByText(successMessage).isVisible();
    }
}
