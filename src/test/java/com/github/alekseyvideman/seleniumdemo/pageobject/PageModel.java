package com.github.alekseyvideman.seleniumdemo.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class PageModel {

    @SuppressWarnings("unused")
    protected final WebDriver driver;

    public PageModel(WebDriver driver) {
        if (driver == null)
            throw new IllegalArgumentException("WebDriver required. Given null");

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
