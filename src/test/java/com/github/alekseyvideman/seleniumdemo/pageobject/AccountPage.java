package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends PageModel {

    private final By depositTab = By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/button[2]");
    private final By withdrawTab = By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/button[3]");

    private final By depositWithdrawButton = By.className("btn-default");
    private final By moneyAmountInputDeposit = By.xpath("/html/body/div[1]/div/div[2]/div/div[4]/div/form/div/input");
    private final By moneyAmountInputWithdraw = By.xpath("/html/body/div[1]/div/div[2]/div/div[4]/div/form/div/input");
    private final By transactionStatusText = By.xpath("/html/body/div[1]/div/div[2]/div/div[4]/div/span");
    private final By balanceText = By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/strong[2]");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void deposit(int money) {
        driver.navigate().refresh();

        driver.findElement(depositTab).click();
        driver.findElement(moneyAmountInputDeposit).sendKeys(String.valueOf(money));
        driver.findElement(depositWithdrawButton).submit();
    }

    public void withdraw(int money) {
        driver.navigate().refresh();

        driver.findElement(withdrawTab).click();
        driver.findElement(moneyAmountInputWithdraw).sendKeys(String.valueOf(money));
        driver.findElement(depositWithdrawButton).submit();
    }

    public String getTransactionStatus() {
        return driver.findElement(transactionStatusText).getText();
    }

    public int getBalance() {
        return Integer.parseInt(driver.findElement(balanceText).getText());
    }
}
