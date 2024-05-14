package com.github.alekseyvideman.seleniumdemo.transaction.dto;

import com.github.alekseyvideman.seleniumdemo.transaction.TransactionType;

import java.time.LocalDateTime;

public record TransactionLog(LocalDateTime dateTime, Long amount, TransactionType type) {}
