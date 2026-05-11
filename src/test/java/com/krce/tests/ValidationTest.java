package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.LoginPage;
import com.krce.pages.ManagerPage;
import com.krce.pages.NewCustomerPage;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidationTest extends BaseTest {

    private NewCustomerPage goToNewCustomerPage() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();
        return new NewCustomerPage(driver, wait);
    }

    @Test(priority = 1)
    public void testEmptyNameField() {
        NewCustomerPage ncp = goToNewCustomerPage();
        ncp.enterName("");
        ncp.tabOutOfName();
        String error = ncp.getNameError();
        Assert.assertFalse(error.isEmpty(), "Name error should appear");
        System.out.println("Name error: " + error);
    }

    @Test(priority = 2)
    public void testEmptyCityField() {
        NewCustomerPage ncp = goToNewCustomerPage();
        ncp.enterCity("");
        ncp.tabOutOfCity();
        String error = ncp.getCityError();
        Assert.assertFalse(error.isEmpty(), "City error should appear");
        System.out.println("City error: " + error);
    }

    @Test(priority = 3)
    public void testNonNumericPin() {
        NewCustomerPage ncp = goToNewCustomerPage();
        ncp.enterPin("abcdef");
        ncp.tabOutOfPin();
        String error = ncp.getPinError();
        Assert.assertFalse(error.isEmpty(), "PIN error should appear for alphabets");
        System.out.println("PIN error: " + error);
    }

    @Test(priority = 4)
    public void testNonNumericMobile() {
        NewCustomerPage ncp = goToNewCustomerPage();
        ncp.enterMobile("abcdefghij");
        ncp.tabOutOfMobile();
        String error = ncp.getMobileError();
        Assert.assertFalse(error.isEmpty(), "Mobile error should appear for alphabets");
        System.out.println("Mobile error: " + error);
    }

    @Test(priority = 5)
    public void testInvalidEmail() {
        NewCustomerPage ncp = goToNewCustomerPage();
        ncp.enterEmail("invalid-email");
        ncp.tabOutOfEmail();
        String error = ncp.getEmailError();
        Assert.assertFalse(error.isEmpty(), "Email error should appear for invalid format");
        System.out.println("Email error: " + error);
    }
}
