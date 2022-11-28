package com.scraper.website;

import com.scraper.price.EuroToCents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaltaPark extends PageObject{
    // contains methods to navigate through the website

    // EuroToCents called to change the String to a price
    EuroToCents euroToCents;
    WebDriverWait wait;

    public MaltaPark(WebDriver driver, WebDriverWait wait, EuroToCents euroToCents){
        super(driver);
        this.wait = wait;
        this.euroToCents = euroToCents;
    }

    public WebElement getClosePopup() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));//maltapark's popup close button sometimes skips seconds until it unlocks visibility
        By XpathButtonIsCloseable = By.xpath("//*[@id=\"okbutton\" and contains(., 'Close')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(XpathButtonIsCloseable));
        wait.until(ExpectedConditions.elementToBeClickable(XpathButtonIsCloseable));
        return driver.findElement(XpathButtonIsCloseable);
    }

    public WebElement getSearchField() {
        By bySearchFieldId = By.id("search");
        wait.until(ExpectedConditions.visibilityOfElementLocated(bySearchFieldId));
        return driver.findElement(bySearchFieldId);
    }

    public WebElement getSearchButton() {
        By bySearchXpath = By.xpath("//button[contains(@class, 'btn-search')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(bySearchXpath));
        return driver.findElement(bySearchXpath);
    }

    public String getItemHeading() {
        By byItemHeadingXpath = By.xpath("//h1[@class='top-title']/span");
        WebElement webElement = driver.findElement(byItemHeadingXpath);
        return webElement.getText();
    }

    public String getItemDescription() {
        By byItemHeadingXpath = By.xpath("//div[@class='readmore-wrapper']");
        WebElement webElement = driver.findElement(byItemHeadingXpath);
        return webElement.getText();
    }

    public String getItemUrl() {
        return driver.getCurrentUrl();
    }

    public String getItemImage() {
        By byItemImageXpath = By.xpath("//a[@class='fancybox']");
        WebElement webElement = driver.findElement(byItemImageXpath);
        return webElement.getAttribute("href");
    }
    //calls euroToCents class
    public int getItemPriceInCents() {
        By byItemPriceXpath = By.xpath("//h1[@class='top-price']");
        WebElement webElement = driver.findElement(byItemPriceXpath);
        String originalPrice = webElement.getText();
        return euroToCents.convert(originalPrice);
    }

    public int getItemAlertType() {
        int alertType;
        String itemCatagory = getCategoryOfItem();

        // determine subcatagories and assign them an alertType
        alertType = switch (itemCatagory) {
            // Car
            case "Cars", "Motorcycles", "Quad Bikes", "Scooters", "Vans & Trucks", "Vehicle Parts", "Other" -> 1;

            // Boat
            case "Marine" -> 2;

            // PropertyForRent
            case "Long Lets", "Short / Holiday Lets" -> 3;

            // PropertyForSale
            case "Property For Sale" -> 4;

            // Toys
            case "Dolls & Bears", "Toys" -> 5;

            // Electronics
            case "Cameras & Photo", "Computers & Office", "Consumer Electronics", "Home Appliances", "Networking & Telecom", "PDAs", "TV", "Video Games" -> 6;

            // Anything which does not fall under these catagories or subcatagories is invalid
            default -> -1;
        };
        return alertType;
    }

    public String getCategoryOfItem() {
        By byItemCategoryXpath = By.xpath("//div[contains(@class,'ui') and contains(@class,'list') and contains(@class,'fixed-label') and contains(@class,'item-details')]/div[3]");
        WebElement webElement = driver.findElement(byItemCategoryXpath);
        return webElement.getText().split("Category:")[1];
    }

    public List<String> getFirstNItemsUrls(int n) {
        By byCommonClassXpath =  By.xpath("//div[contains(@class,'ui') and contains(@class,'items') and contains(@class,'listings')]/div[contains(@class,'item')]/*/a[@class='header']");
        List<WebElement> allLinks = driver.findElements(byCommonClassXpath);
        List<String> urls = new ArrayList<>(n);

        allLinks.forEach(element -> urls.add(element.getAttribute("href")));
        return urls.stream().limit(n).collect(Collectors.toList());
    }

}
