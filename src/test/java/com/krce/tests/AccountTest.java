package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.*;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest extends BaseTest {

    // saved for FundTransferTest
    public static String savedAccountId = "";
    public static String savedAccountId2 = "";

    private void loginAsManager() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(priority = 1)
    public void testCreateNewAccount() {
        loginAsManager();

        // first create a customer for the account
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();
        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        String email = "acct" + System.currentTimeMillis() + "@mail.com";
        ncp.fillAndSubmit("Account User", "male", "03/10/1990",
                "Acct Street", "Delhi", "Delhi",
                "110001", "9111111111", email, "Acct@123");
        String custId = ncp.getCustomerId();
        System.out.println("Customer created: " + custId);

        // now create savings account
        mgr.clickNewAccount();
        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount(custId, "Savings", "50000");

        Assert.assertTrue(nap.isAccountCreated(), "Account should be created");
        savedAccountId = nap.getAccountId();
        Assert.assertFalse(savedAccountId.isEmpty(), "Account ID should be generated");
        System.out.println("Account created: " + savedAccountId);
    }

    @Test(priority = 2)
    public void testCreateSecondAccount() {
        loginAsManager();

        // create another customer + account for fund transfer
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();
        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        String email = "acct2" + System.currentTimeMillis() + "@mail.com";
        ncp.fillAndSubmit("Second User", "female", "07/22/1993",
                "Second Street", "Mumbai", "Maharashtra",
                "400001", "9222222222", email, "Acct@456");
        String custId = ncp.getCustomerId();

        mgr.clickNewAccount();
        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount(custId, "Savings", "30000");

        Assert.assertTrue(nap.isAccountCreated());
        savedAccountId2 = nap.getAccountId();
        System.out.println("Second account created: " + savedAccountId2);
    }

    @Test(priority = 3)
    public void testInvalidCustomerIdAccount() {
        loginAsManager();
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewAccount();

        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount("99999", "Savings", "1000");

        String alertText = nap.getAlertText();
        Assert.assertFalse(alertText.isEmpty(), "Should show error for invalid customer ID");
        System.out.println("Invalid customer alert: " + alertText);
    }
}
