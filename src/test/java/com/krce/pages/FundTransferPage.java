package com.krce.pages;

import com.krce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FundTransferPage extends BasePage {
    private final By payerField = By.name("payersaccount");
    private final By payeeField = By.name("payeeaccount");
    private final By amountField = By.name("ammount");
    private final By descField = By.name("desc");
    private final By submitButton = By.name("AccSubmit");
    private final By successMsg = By.xpath("//p[contains(text(),'Fund Transfer Details')]");

    public FundTransferPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterPayer(String accNo) {
        typeText(payerField, accNo);
    }

    public void enterPayee(String accNo) {
        typeText(payeeField, accNo);
    }

    public void enterAmount(String amt) {
        typeText(amountField, amt);
    }

    public void enterDescription(String desc) {
        typeText(descField, desc);
    }

    public void clickSubmit() {
        clickElement(submitButton);
    }

    public void transferFunds(String payer, String payee, String amount, String desc) {
        enterPayer(payer);
        enterPayee(payee);
        enterAmount(amount);
        enterDescription(desc);
        clickSubmit();
    }

    public boolean isTransferSuccess() {
        return isElementDisplayed(successMsg);
    }
}
