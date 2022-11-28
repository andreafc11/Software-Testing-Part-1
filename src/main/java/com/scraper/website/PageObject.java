package com.scraper.website;

import org.openqa.selenium.WebDriver;

public abstract class PageObject {
    public WebDriver driver;
    public PageObject(WebDriver driver) {
        this.driver = driver;
    }
}
