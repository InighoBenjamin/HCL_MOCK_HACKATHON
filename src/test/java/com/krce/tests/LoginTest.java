package com.krce.tests;
import com.krce.pages.LoginPage;
import com.krce.base.BaseTest;
import com.krce.pages.ManagerPage;
import com.krce.utils.ConfigReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

        System.out.println("Valid login verified");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"wrongUser", "wrongPass"},
                {"mngr12345", "wrongPass"},
                {"wrongUser", "etytEmY"}
        };
    }

    @Test(priority = 2, dataProvider = "invalidLoginData")
    public void invalidLoginTest(String username, String password) {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login(username, password);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();

        Assert.assertTrue(alertText.contains("User or Password is not valid"));

        alert.accept();

        System.out.println("Invalid login error message verified");
    }

    @Test(priority = 3)
    public void logoutRedirectTest() {

        LoginPage loginPage = new LoginPage(driver, wait);
        ManagerPage managerPage = new ManagerPage(driver, wait);

        String username = ConfigReader.getValue("username");
        String password = ConfigReader.getValue("password");

        loginPage.login(username, password);

        managerPage.clickLogout();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        alert.accept();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed());

        System.out.println("Logout redirect verified");
    }

    @Test(priority = 4)
    public void blankFieldValidationTest() {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.clickUserIdBox();
        loginPage.clickPasswordBox();

        String userIdError = loginPage.getUserIdError();

        loginPage.clickUserIdBox();

        String passwordError = loginPage.getPasswordError();

        Assert.assertTrue(userIdError.contains("User-ID must not be blank"));
        Assert.assertTrue(passwordError.contains("Password must not be blank"));

        System.out.println("Blank field validation verified");
    }
}