package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private final By userIdField = By.name("uid");
    private final By passwordField = By.name("password");
    private final By loginButton = By.name("btnLogin");
    private final By loginHeading = By.xpath("//h2[contains(text(),'Guru99 Bank')]");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openLoginPage(String url) {
        driver.get(url);
    }

    public void enterUserId(String uid) {
        typeText(userIdField, uid);
    }

    public void enterPassword(String pwd) {
        typeText(passwordField, pwd);
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void login(String uid, String pwd) {
        enterUserId(uid);
        enterPassword(pwd);
        clickLogin();
    }

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginHeading);
    }
}
