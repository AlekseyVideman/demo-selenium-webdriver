package com.github.alekseyvideman.seleniumdemo.pageobject;

import com.github.alekseyvideman.seleniumdemo.mapper.TransactionLogWebElementMapper;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;
import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
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

        var wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(transactionTableRow));

        return driver.findElements(transactionTableRow).stream()
                .map(transactionLogWebElementMapper::map)
                .toList();
    }

    public void printCsv(List<TransactionLog> a) {
        try (CSVWriter writer = new CSVWriter(new FileWriter("transactions-report.csv"))) {
            writer.writeAll(convertHistoryToCsvData(a));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> convertHistoryToCsvData(List<TransactionLog> history) {
        var result = new ArrayList<String[]>();
        result.add(new String[] {"DATETIME", "AMOUNT", "TYPE"});

        var list = history.stream()
                .map(transactionLog -> new String[] {
                        transactionLog.dateTime().toString(),
                        transactionLog.amount().toString(),
                        transactionLog.type().toString()
                })
                .toList();

        result.addAll(list);
        return result;
    }

}
