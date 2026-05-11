package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.EditCustomerPage;
import com.krce.pages.LoginPage;
import com.krce.pages.ManagerPage;
import com.krce.pages.NewCustomerPage;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomerTest extends BaseTest {

    // saved for other test classes to use
    public static String savedCustomerId = "";
    public static String savedEmail = "";

    private void loginAsManager() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(priority = 1)
    public void testCreateNewCustomer() {
        loginAsManager();
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();

        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        savedEmail = "test" + System.currentTimeMillis() + "@mail.com";

        ncp.fillAndSubmit("John Kumar", "male", "01/15/1995",
                "123 Test Street", "Chennai", "Tamil Nadu",
                "600001", "9876543210", savedEmail, "Test@123");

        Assert.assertTrue(ncp.isSuccessDisplayed(), "Success message should appear");
        savedCustomerId = ncp.getCustomerId();
        Assert.assertFalse(savedCustomerId.isEmpty(), "Customer ID should be generated");
        System.out.println("Customer created: " + savedCustomerId);
    }

    @Test(priority = 2, dependsOnMethods = "testCreateNewCustomer")
    public void testEditCustomerAddress() {
        loginAsManager();
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickEditCustomer();

        EditCustomerPage ecp = new EditCustomerPage(driver, wait);
        ecp.enterCustomerId(savedCustomerId);
        ecp.submitCustomerId();
        ecp.editAddress("456 Updated Street");
        ecp.clickUpdate();

        Assert.assertTrue(ecp.isUpdateSuccess(), "Customer should be updated");
        System.out.println("Customer address updated");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateNewCustomer")
    public void testDuplicateEmail() {
        loginAsManager();
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();

        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        ncp.fillAndSubmit("Duplicate User", "female", "05/20/1998",
                "Dup Address", "Mumbai", "Maharashtra",
                "400001", "9123456789", savedEmail, "Pass@123");

        // duplicate email shows alert
        String alertText = ncp.getAlertText();
        Assert.assertFalse(alertText.isEmpty(), "Should show error for duplicate email");
        System.out.println("Duplicate email alert: " + alertText);
    }
}
