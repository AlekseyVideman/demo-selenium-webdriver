package com.github.alekseyvideman.seleniumdemo.mapper;

import com.github.alekseyvideman.seleniumdemo.transaction.TransactionType;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TransactionLogWebElementMapper {

    public TransactionLog map(WebElement tr) {
        var td = tr.findElements(By.tagName("td"));

        var formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);
        var dateTime = LocalDateTime.parse(td.get(0).getText(), formatter);

        long amount = Long.parseLong(td.get(1).getText());

        var type = TransactionType.valueOf(td.get(2).getText().toUpperCase());

        return new TransactionLog(dateTime, amount, type);
    }
}
