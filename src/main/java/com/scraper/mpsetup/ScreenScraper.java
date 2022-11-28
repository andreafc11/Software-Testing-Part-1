package com.scraper.mpsetup;

import com.scraper.price.EuroToCents;
import com.scraper.models.Item;
import com.scraper.website.MaltaPark;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ScreenScraper {
    // calls upon the maltapark class to visit the website, close the popup, search for an item and upload it to marketalerts
    WebDriver driver;
    WebDriverWait wait;
    EuroToCents euroToCents;
    MaltaPark maltapark;
    List<Item> itemList;
    HelpRequest helpRequest;
    MakeRequest makeRequest;

    public ScreenScraper(WebDriver driver, WebDriverWait wait, EuroToCents euroToCents, MaltaPark maltapark, List<Item> itemList, HelpRequest helpRequest, MakeRequest makeRequest) {
        this.driver = driver;
        this.wait = wait;

        this.maltapark = maltapark;
        this.itemList = itemList;
        this.euroToCents = euroToCents;
        this.helpRequest = helpRequest;
        this.makeRequest = makeRequest;
    }

    public void visitWebsite(String websiteUrl) {
        driver.manage().window().maximize();
        driver.get(websiteUrl);
    }

    public void closePopup(){
        WebElement closePopup = maltapark.getClosePopup();
        closePopup.click();
    }

    public void searchItemByWordSearch(String search){
        WebElement searchField = maltapark.getSearchField();
        WebElement searchButton = maltapark.getSearchButton();
        searchField.sendKeys(search);
        searchButton.submit();
    }
    public void stop() {
        driver.quit();
    }

    public void scrapeFirstNResults(int n) {
        List<String> firstNLinks = maltapark.getFirstNItemsUrls(n);

        for (String itemLink: firstNLinks) {
            visitWebsite(itemLink);

            Item item = new Item();
            //get data
            int alertType = maltapark.getItemAlertType();
            String heading = maltapark.getItemHeading();
            String description = maltapark.getItemDescription();
            String url = maltapark.getItemUrl();
            String imageUrl = maltapark.getItemImageUrl();
            int priceInCents = maltapark.getItemPriceInCents();

            // setter injection
            item.setAlertType(alertType);
            item.setHeading(heading);
            item.setDescription(description);
            item.setUrl(url);
            item.setImageUrl(imageUrl);
            item.setPriceInCents(priceInCents);

            itemList.add(item);
        }
    }

    public void uploadItemListToMarketAlert(){
        for (Item item: itemList) {
            makeRequest.setJSONObject(item);
            helpRequest.setMakeRequest(makeRequest);
            helpRequest.makePost();
        }
    }

    public void setItemList(List<Item> itemList){
        this.itemList = itemList;
    }

}
