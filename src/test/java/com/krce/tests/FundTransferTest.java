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

    private String createCustomerAndGetAccount(String name, String gender, String dob,
                                                String email, String deposit) {
        ManagerPage mgr = new ManagerPage(driver, wait);
        mgr.clickNewCustomer();

        NewCustomerPage ncp = new NewCustomerPage(driver, wait);
        ncp.fillAndSubmit(name, gender, dob,
                "Test Addr", "Chennai", "TamilNadu", "600001",
                "9111111111", email, "Pass@1");

        // check for alert
        String alert = ncp.getAlertText();
        if (!alert.isEmpty()) {
            System.out.println("Alert: " + alert);
            return null;
        }

        if (!ncp.isSuccessDisplayed()) {
            return null;
        }

        String custId = ncp.getCustomerId();
        System.out.println("Customer: " + custId);

        mgr.clickNewAccount();
        NewAccountPage nap = new NewAccountPage(driver, wait);
        nap.createAccount(custId, "Savings", deposit);

        if (!nap.isAccountCreated()) {
            nap.getAlertText();
            return null;
        }
        return nap.getAccountId();
    }

    @Test(priority = 1)
    public void testValidFundTransfer() {
        loginAsManager();

        // create payer
        String ts = String.valueOf(System.currentTimeMillis());
        String payerAcct = createCustomerAndGetAccount("Payer User", "male", "10/01/1990",
                "payer_" + ts + "@mail.com", "50000");
        Assert.assertNotNull(payerAcct, "Payer account should be created");
        System.out.println("Payer account: " + payerAcct);

        // create payee - use new login session to avoid DOB caching issues
        loginAsManager();
        String payeeAcct = createCustomerAndGetAccount("Payee User", "female", "20/05/1992",
                "payee_" + ts + "@mail.com", "10000");
        Assert.assertNotNull(payeeAcct, "Payee account should be created");
        System.out.println("Payee account: " + payeeAcct);

        // do fund transfer
        ManagerPage mgr = new ManagerPage(driver, wait);
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
