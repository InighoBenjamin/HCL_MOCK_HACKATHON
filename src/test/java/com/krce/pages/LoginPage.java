package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    By userIdTextbox = By.name("uid");
    By passwordTextbox = By.name("password");
    By loginButton = By.name("btnLogin");
    By resetButton = By.name("btnReset");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterUserId(String userId) {
        typeText(userIdTextbox, userId);
    }

    public void enterPassword(String password) {
        typeText(passwordTextbox, password);
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void clickReset() {
        clickElement(resetButton);
    }

    public void login(String userId, String password) {
        enterUserId(userId);
        enterPassword(password);
        clickLogin();
    }

    public String getAlertTextAndAccept() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}