package com.github.alekseyvideman.seleniumdemo.pageobject;

import com.github.alekseyvideman.seleniumdemo.mapper.TransactionLogWebElementMapper;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TransactionsPage extends PageModel {

    private final TransactionLogWebElementMapper transactionLogWebElementMapper;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[3]/button[1]")
    private WebElement transactionsTab;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[2]/table/tbody/tr")
    private List<WebElement> transactionTableRow;

    public TransactionsPage(WebDriver driver, TransactionLogWebElementMapper transactionLogWebElementMapper) {
        super(driver);
        this.transactionLogWebElementMapper = transactionLogWebElementMapper;
    }

    public List<TransactionLog> getHistory() {
        transactionsTab.click();

        return transactionTableRow.stream()
                .map(transactionLogWebElementMapper::map)
                .toList();
    }

}
