package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends PageModel {

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[3]/button[2]")
    private WebElement depositTab;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[3]/button[3]")
    private WebElement withdrawTab;

    @FindBy(className = "btn-default")
    private WebElement depositWithdrawButton;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[4]/div/form/div/input")
    private  WebElement moneyAmountInputDeposit;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[4]/div/form/div/input")
    private  WebElement moneyAmountInputWithdraw;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[4]/div/span")
    private  WebElement transactionStatusText;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[2]/strong[2]")
    private  WebElement balanceText;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void deposit(int money) {
        driver.navigate().refresh();

        depositTab.click();
        moneyAmountInputDeposit.sendKeys(String.valueOf(money));
        depositWithdrawButton.submit();
    }

    public void withdraw(int money) {
        driver.navigate().refresh();

        withdrawTab.click();
        moneyAmountInputWithdraw.sendKeys(String.valueOf(money));
        depositWithdrawButton.submit();
    }

    public String getTransactionStatus() {
        return transactionStatusText.getText();
    }

    public int getBalance() {
        return Integer.parseInt(balanceText.getText());
    }
}
