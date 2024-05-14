package com.github.alekseyvideman.seleniumdemo.service;

import com.opencsv.CSVWriter;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;

import java.io.IOException;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionLogCsvReportingService {

    public String generateCsv(List<TransactionLog> transactionLogs) {
        var sw = new StringWriter();
        try (CSVWriter writer = new CSVWriter(sw)) {
            writer.writeAll(convertTransactionLogToCsv(transactionLogs));

            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> convertTransactionLogToCsv(List<TransactionLog> transactionLogs) {
        var dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);

        var result = new ArrayList<String[]>();
        result.add(new String[] {"DATETIME", "AMOUNT", "TYPE"});

        var list = transactionLogs.stream()
                .map(transactionLog -> new String[] {
                        transactionLog.dateTime().format(dateFormatter),
                        transactionLog.amount().toString(),
                        transactionLog.type().toString()
                })
                .toList();

        result.addAll(list);
        return result;
    }

}
