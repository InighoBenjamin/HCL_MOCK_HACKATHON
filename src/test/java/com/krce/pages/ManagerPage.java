package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManagerPage extends BasePage {
    private final By welcomeMsg = By.xpath("//marquee[contains(text(),'Welcome')]");
    private final By newCustomerLink = By.xpath("//a[contains(text(),'New Customer')]");
    private final By editCustomerLink = By.xpath("//a[contains(text(),'Edit Customer')]");
    private final By newAccountLink = By.xpath("//a[contains(text(),'New Account')]");
    private final By editAccountLink = By.xpath("//a[contains(text(),'Edit Account')]");
    private final By fundTransferLink = By.xpath("//a[contains(text(),'Fund Transfer')]");
    private final By logoutLink = By.xpath("//a[contains(text(),'Log out')]");

    public ManagerPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isWelcomeDisplayed() {
        return isElementDisplayed(welcomeMsg);
    }

    public void clickNewCustomer() {
        clickElement(newCustomerLink);
    }

    public void clickEditCustomer() {
        clickElement(editCustomerLink);
    }

    public void clickNewAccount() {
        clickElement(newAccountLink);
    }

    public void clickEditAccount() {
        clickElement(editAccountLink);
    }

    public void clickFundTransfer() {
        clickElement(fundTransferLink);
    }

    public void clickLogout() {
        clickElement(logoutLink);
    }
}
