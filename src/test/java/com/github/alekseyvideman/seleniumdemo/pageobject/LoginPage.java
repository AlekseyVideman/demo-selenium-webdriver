package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends PageModel {

    private final By customerLogin = By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[1]/button");
    private final By userSelect = By.id("userSelect");
    private final By loginButton = By.xpath("/html/body/div[1]/div/div[2]/div/form/button");
    private final By customerName = By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/strong/span");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String userName) {
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.findElement(customerLogin).click();

        var selectElement = driver.findElement(userSelect);
        var select = new Select(selectElement);
        select.selectByVisibleText(userName);
        
        driver.findElement(loginButton).click();
    }

    public String getCustomerNameText() {
        return driver.findElement(customerName).getText();
    }
}
