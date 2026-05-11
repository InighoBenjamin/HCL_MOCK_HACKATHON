package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditAccountPage extends BasePage {
    private final By accountNoField = By.name("accountno");
    private final By submitAccNo = By.name("AccSubmit");
    private final By accountType = By.name("a_type");
    private final By updateButton = By.name("AccSubmit");
    private final By successMsg = By.xpath("//p[contains(text(),'Account details updated')]");

    public EditAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterAccountNo(String accNo) {
        typeText(accountNoField, accNo);
    }

    public void submitAccountNo() {
        clickElement(submitAccNo);
    }

    public void changeAccountType(String type) {
        Select dropdown = new Select(waitForElement(accountType));
        dropdown.selectByVisibleText(type);
    }

    public void clickUpdate() {
        clickElement(updateButton);
    }

    public boolean isUpdateSuccess() {
        return isElementDisplayed(successMsg);
    }
}
