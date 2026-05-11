package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewCustomerPage extends BasePage {
    private final By nameField = By.name("name");
    private final By genderMale = By.cssSelector("input[value='m']");
    private final By genderFemale = By.cssSelector("input[value='f']");
    private final By dobField = By.name("dob");
    private final By addressField = By.name("addr");
    private final By cityField = By.name("city");
    private final By stateField = By.name("state");
    private final By pinField = By.name("pinno");
    private final By mobileField = By.name("telephoneno");
    private final By emailField = By.name("emailid");
    private final By passwordField = By.name("password");
    private final By submitButton = By.name("sub");

    // success page elements
    private final By successMsg = By.xpath("//p[contains(text(),'Customer Registered Successfully')]");
    private final By custIdValue = By.xpath("//td[contains(text(),'Customer ID')]/following-sibling::td");

    // validation error labels
    private final By nameError = By.id("message");
    private final By cityError = By.id("message4");
    private final By stateError = By.id("message5");
    private final By pinError = By.id("message6");
    private final By mobileError = By.id("message7");
    private final By emailError = By.id("message9");

    public NewCustomerPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterName(String name) {
        typeText(nameField, name);
    }

    public void selectGender(String gender) {
        if (gender.equals("female")) {
            clickElement(genderFemale);
        } else {
            clickElement(genderMale);
        }
    }

    public void enterDob(String dob) {
        // Guru99 DOB is type="date" - try sendKeys first, then JS fallback
        WebElement el = waitForElement(dobField);
        el.click();
        el.sendKeys(dob);

        // verify if value was set - if empty, use JavaScript fallback
        String currentVal = el.getAttribute("value");
        if (currentVal == null || currentVal.isEmpty()) {
            // convert dd/mm/yyyy to yyyy-mm-dd for JS
            String[] parts = dob.split("/");
            if (parts.length == 3) {
                String isoDate = parts[2] + "-" + parts[1] + "-" + parts[0];
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value = arguments[1];", el, isoDate);
            }
        }
    }

    public void enterAddress(String addr) {
        typeText(addressField, addr);
    }

    public void enterCity(String city) {
        typeText(cityField, city);
    }

    public void enterState(String state) {
        typeText(stateField, state);
    }

    public void enterPin(String pin) {
        typeText(pinField, pin);
    }

    public void enterMobile(String mobile) {
        typeText(mobileField, mobile);
    }

    public void enterEmail(String email) {
        typeText(emailField, email);
    }

    public void enterPassword(String pwd) {
        typeText(passwordField, pwd);
    }

    public void clickSubmit() {
        clickElement(submitButton);
    }

    public void fillAndSubmit(String name, String gender, String dob, String addr,
                              String city, String state, String pin, String mobile,
                              String email, String pwd) {
        enterName(name);
        selectGender(gender);
        enterDob(dob);
        enterAddress(addr);
        enterCity(city);
        enterState(state);
        enterPin(pin);
        enterMobile(mobile);
        enterEmail(email);
        enterPassword(pwd);
        clickSubmit();
    }

    public boolean isSuccessDisplayed() {
        return isElementDisplayed(successMsg);
    }

    public String getCustomerId() {
        return getElementText(custIdValue);
    }

    // validation methods
    public void tabOutOfName() {
        driver.findElement(nameField).sendKeys(Keys.TAB);
    }

    public void tabOutOfCity() {
        driver.findElement(cityField).sendKeys(Keys.TAB);
    }

    public void tabOutOfPin() {
        driver.findElement(pinField).sendKeys(Keys.TAB);
    }

    public void tabOutOfMobile() {
        driver.findElement(mobileField).sendKeys(Keys.TAB);
    }

    public void tabOutOfEmail() {
        driver.findElement(emailField).sendKeys(Keys.TAB);
    }

    public String getNameError() { return getElementText(nameError); }
    public String getCityError() { return getElementText(cityError); }
    public String getStateError() { return getElementText(stateError); }
    public String getPinError() { return getElementText(pinError); }
    public String getMobileError() { return getElementText(mobileError); }
    public String getEmailError() { return getElementText(emailError); }
}
