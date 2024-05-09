package com.github.alekseyvideman.seleniumdemo.pageobject;

import com.github.alekseyvideman.seleniumdemo.mapper.TransactionLogWebElementMapper;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TransactionsPage extends PageModel {

    private final TransactionLogWebElementMapper transactionLogWebElementMapper;

    private final By transactionsTab = By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/button[1]");
    private final By transactionTableRow = By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/table/tbody/tr");

    public TransactionsPage(WebDriver driver, TransactionLogWebElementMapper transactionLogWebElementMapper) {
        super(driver);
        this.transactionLogWebElementMapper = transactionLogWebElementMapper;
    }

    public List<TransactionLog> getHistory() {
        driver.findElement(transactionsTab).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(transactionTableRow));

        return driver.findElements(transactionTableRow).stream()
                .map(transactionLogWebElementMapper::map)
                .toList();
    }

}
