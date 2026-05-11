package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.*;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest extends BaseTest {

    public static String savedAccountId = "";
    public static String savedAccountId2 = "";

    private void loginAsManager() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    private String createCustomerAndAccount(String name, String gender, String dob,
                                             String email, String deposit) {
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();

        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        ncp.fillAndSubmit(name, gender, dob,
                "Test Street", "Chennai", "TamilNadu", "600001",
                "9876543210", email, "Pass@123");

        // check for alert first (e.g. "please fill all fields")
        String alert = ncp.getAlertText();
        if (!alert.isEmpty()) {
            System.out.println("Customer creation alert: " + alert);
            return null;
        }

        if (!ncp.isSuccessDisplayed()) {
            System.out.println("Customer creation did not show success page");
            return null;
        }

        String custId = ncp.getCustomerId();
        System.out.println("Customer created: " + custId);

        mgr.clickNewAccount();
        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount(custId, "Savings", deposit);

        if (!nap.isAccountCreated()) {
            String acctAlert = nap.getAlertText();
            System.out.println("Account creation alert: " + acctAlert);
            return null;
        }

        return nap.getAccountId();
    }

    @Test(priority = 1)
    public void testCreateNewAccount() {
        loginAsManager();

        String email = "acct" + System.currentTimeMillis() + "@mail.com";
        String acctId = createCustomerAndAccount("Account User", "male", "10/03/1990",
                email, "50000");

        Assert.assertNotNull(acctId, "Account should be created");
        savedAccountId = acctId;
        System.out.println("Account created: " + savedAccountId);
    }

    @Test(priority = 2)
    public void testCreateSecondAccount() {
        loginAsManager();

        String email = "acct2" + System.currentTimeMillis() + "@mail.com";
        String acctId = createCustomerAndAccount("Second User", "female", "22/07/1993",
                email, "30000");

        Assert.assertNotNull(acctId, "Second account should be created");
        savedAccountId2 = acctId;
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
