package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManagerPage extends BasePage {

    By managerIdText = By.xpath("//td[contains(text(),'Manger Id')]");
    By logoutLink = By.linkText("Log out");
    By newCustomerLink = By.linkText("New Customer");

    public ManagerPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getManagerIdText() {
        return getElementText(managerIdText);
    }

    public void clickLogout() {
        jsClick(logoutLink);
    }

    public void clickNewCustomer() {
        clickElement(newCustomerLink);
    }
}