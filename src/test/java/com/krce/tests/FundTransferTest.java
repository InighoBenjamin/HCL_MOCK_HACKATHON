package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.*;
import com.krce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FundTransferTest extends BaseTest {

    private void loginAsManager() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(priority = 1)
    public void testValidFundTransfer() {
        loginAsManager();

        // create 2 customers + 2 accounts for transfer
        ManagerPage mgr = new ManagerPage(driver, wait);

        // customer 1
        mgr.clickNewCustomer();
        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        String email1 = "ft1_" + System.currentTimeMillis() + "@mail.com";
        ncp.fillAndSubmit("Payer User", "male", "01/10/1990",
                "Addr1", "Chennai", "TN", "600001", "9111111111", email1, "Pass@1");
        String custId1 = ncp.getCustomerId();

        mgr.clickNewAccount();
        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount(custId1, "Savings", "50000");
        String payerAcct = nap.getAccountId();

        // customer 2
        mgr.clickNewCustomer();
        NewCustomerPage ncp2 = new NewCustomerPage(driver, wait);
        String email2 = "ft2_" + System.currentTimeMillis() + "@mail.com";
        ncp2.fillAndSubmit("Payee User", "female", "05/20/1992",
                "Addr2", "Mumbai", "MH", "400001", "9222222222", email2, "Pass@2");
        String custId2 = ncp2.getCustomerId();

        mgr.clickNewAccount();
        NewAccountPage nap2 = new NewAccountPage(driver, wait);
        nap2.createAccount(custId2, "Savings", "10000");
        String payeeAcct = nap2.getAccountId();

        // do fund transfer
        mgr.clickFundTransfer();
        FundTransferPage ftp = new FundTransferPage(driver, wait);
        ftp.transferFunds(payerAcct, payeeAcct, "5000", "Test transfer");

        Assert.assertTrue(ftp.isTransferSuccess(), "Fund transfer should succeed");
        System.out.println("Fund transfer passed: " + payerAcct + " -> " + payeeAcct);
    }

    @Test(priority = 2)
    public void testInvalidPayeeTransfer() {
        loginAsManager();
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickFundTransfer();

        FundTransferPage ftp = new FundTransferPage(driver, wait);
        ftp.transferFunds("12345", "99999", "1000", "Invalid test");

        String alertText = ftp.getAlertText();
        Assert.assertFalse(alertText.isEmpty(), "Should show error for invalid accounts");
        System.out.println("Invalid transfer alert: " + alertText);
    }
}
