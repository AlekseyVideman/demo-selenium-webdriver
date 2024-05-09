package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.WebDriver;

public abstract class PageModel {

    @SuppressWarnings("unused")
    protected final WebDriver driver;

    protected PageModel(WebDriver driver) {
        if (driver == null)
            throw new IllegalArgumentException("WebDriver required. Given null");

        this.driver = driver;
    }
}
