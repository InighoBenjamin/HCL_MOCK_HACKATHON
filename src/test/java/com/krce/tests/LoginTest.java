package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.LoginPage;
import com.krce.pages.ManagerPage;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
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
}