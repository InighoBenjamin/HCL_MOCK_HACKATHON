package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditCustomerPage extends BasePage {
    private final By custIdField = By.name("cusid");
    private final By submitCustId = By.name("AccSubmit");
    private final By addressField = By.name("addr");
    private final By cityField = By.name("city");
    private final By updateButton = By.name("sub");

    // success indicators
    private final By successHeading = By.xpath("//*[contains(text(),'successfully') or contains(text(),'Successfully')]");
    // customer details form loaded successfully (proves edit page works)
    private final By customerNameField = By.name("name");

    public EditCustomerPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterCustomerId(String id) {
        typeText(custIdField, id);
    }

    public void submitCustomerId() {
        clickElement(submitCustId);
    }

    public boolean isCustomerFormLoaded() {
        return isElementDisplayed(customerNameField);
    }

    public void editAddress(String addr) {
        typeText(addressField, addr);
    }

    public void editCity(String city) {
        typeText(cityField, city);
    }

    public void clickUpdate() {
        clickElement(updateButton);
    }

    public boolean isUpdateSuccess() {
        // check for alert (success or error)
        String alertText = getAlertText();
        if (!alertText.isEmpty()) {
            return alertText.toLowerCase().contains("success") || alertText.toLowerCase().contains("updated");
        }
        // check for page heading
        return isElementDisplayed(successHeading);
    }
}
