package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.LoginPage;
import com.krce.pages.ManagerPage;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                {"invalidUser", "invalidPass"},
                {"mngr620818", "wrongPass"},
                {"wrongUser", "YhEbApE"}
        };
    }

    @Test(priority = 1)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        ManagerPage managerPage = new ManagerPage(driver, wait);
        Assert.assertTrue(managerPage.isWelcomeDisplayed(), "Welcome message should appear");
        System.out.println("Valid login test passed");
    }

    @Test(priority = 2, dataProvider = "invalidLogins")
    public void testInvalidLogin(String uid, String pwd) {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(uid, pwd);

        String alertText = loginPage.getAlertText();
        Assert.assertTrue(alertText.contains("not valid"), "Should show invalid login alert");
        System.out.println("Invalid login alert: " + alertText);
    }

    @Test(priority = 3)
    public void testBlankLogin() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.clickLogin();

        // Guru99 shows alert even for blank fields
        String alertText = loginPage.getAlertText();
        if (!alertText.isEmpty()) {
            System.out.println("Blank login alert: " + alertText);
        }
        // after alert, should still be on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should remain on login page");
        System.out.println("Blank login test passed");
    }

    @Test(priority = 4)
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        ManagerPage managerPage = new ManagerPage(driver, wait);
        Assert.assertTrue(managerPage.isWelcomeDisplayed());

        managerPage.clickLogout();
        // handle logout confirmation alert
        String alertText = loginPage.getAlertText();
        System.out.println("Logout alert: " + alertText);

        // after logout, page goes back to login
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("index.php") || currentUrl.contains("guru99"),
                "Should redirect to login page");
        System.out.println("Logout test passed");
    }
    @Test
    public void intentionalFailTest() {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login("wrongUser", "wrongPass");

        String alertText = loginPage.getAlertText();

        Assert.assertTrue(alertText.contains("Login Successful"));

        System.out.println("This line will not print");
    }
}
