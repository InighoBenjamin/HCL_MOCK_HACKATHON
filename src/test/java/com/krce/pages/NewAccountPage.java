package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewAccountPage extends BasePage {
    private final By custIdField = By.name("cusid");
    private final By accountType = By.name("selaccount");
    private final By depositField = By.name("inideposit");
    private final By submitButton = By.name("button2");
    private final By successMsg = By.xpath("//p[contains(text(),'Account Generated Successfully')]");
    private final By accountIdValue = By.xpath("//td[contains(text(),'Account ID')]/following-sibling::td");

    public NewAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterCustomerId(String id) {
        typeText(custIdField, id);
    }

    public void selectAccountType(String type) {
        Select dropdown = new Select(waitForElement(accountType));
        dropdown.selectByVisibleText(type);
    }

    public void enterDeposit(String amount) {
        typeText(depositField, amount);
    }

    public void clickSubmit() {
        clickElement(submitButton);
    }

    public void createAccount(String custId, String type, String deposit) {
        enterCustomerId(custId);
        selectAccountType(type);
        enterDeposit(deposit);
        clickSubmit();
    }

    public boolean isAccountCreated() {
        return isElementDisplayed(successMsg);
    }

    public String getAccountId() {
        return getElementText(accountIdValue);
    }
}
