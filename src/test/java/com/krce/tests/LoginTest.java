package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.LoginPage;
import com.krce.pages.ManagerPage;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void validLoginTest() {

        LoginPage loginPage = new LoginPage(driver, wait);
        ManagerPage managerPage = new ManagerPage(driver, wait);

        String username = ConfigReader.getValue("username");
        String password = ConfigReader.getValue("password");

        loginPage.login(username, password);

        String actualText = managerPage.getManagerIdText();

        Assert.assertTrue(actualText.contains(username));

        System.out.println("Valid login test passed");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {

        return new Object[][] {
                {"wrongUser", "wrongPass"},
                {"mngr12345", "wrongPass"},
                {"wrongUser", "etytEmY"}
        };
    }

    @Test(priority = 2, dataProvider = "invalidLoginData")
    public void invalidLoginTest(String username, String password) {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login(username, password);

        String alertText = loginPage.getAlertTextAndAccept();

        Assert.assertTrue(alertText.contains("User or Password is not valid"));

        System.out.println("Invalid login alert verified: " + alertText);
    }
}