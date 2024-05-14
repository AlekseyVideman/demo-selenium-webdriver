package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends PageModel {

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[1]/div[1]/button")
    private WebElement customerLogin;

    @FindBy(id = "userSelect")
    private WebElement userSelect;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/form/button")
    private WebElement loginButton;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[1]/strong/span")
    private WebElement customerName;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String userName) {
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        customerLogin.click();

        var select = new Select(userSelect);
        select.selectByVisibleText(userName);

        loginButton.click();
    }

    public String getCustomerNameText() {
        return customerName.getText();
    }
}
