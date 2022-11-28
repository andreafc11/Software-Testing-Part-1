package com.scraper.interfacing;

import com.scraper.price.EuroToCents;
import com.scraper.models.Item;
import com.scraper.website.MaltaPark;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import com.scraper.mpsetup.ScreenScraper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
//https://www.javadevjournal.com/java-design-patterns/facade-design-pattern/

public class Facade {
    //the front-most facing interference to be called upon by the main method
    WebDriver driver;
    ScreenScraper screenscraper;
    WebDriverWait wait;
    EuroToCents euroToCents;
    List<Item> itemList;
    HelpRequest helpRequest;
    MakeRequest makeRequest;
    MaltaPark maltapark;
    final String chosenUrl = "https://www.maltapark.com/";

    public Facade(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        System.setProperty("webdriver.chrome.driver", "/Users/owner/Desktop/cpsassignment/chromedriver");

        itemList = new ArrayList<>(5);
        euroToCents = new EuroToCents();
        makeRequest = new MakeRequest();
        helpRequest = new HelpRequest(makeRequest);
        maltapark = new MaltaPark(driver, wait, euroToCents);
        screenscraper = new ScreenScraper(driver, wait, euroToCents, maltapark, itemList, helpRequest, makeRequest);
    }

    //Setting methods to be called by facade
    public void setWebDriver(WebDriver driver) {
        this.driver.quit();
        this.driver = driver;
    }
    public void setScreenScraper(ScreenScraper screenscraper) {
        this.screenscraper = screenscraper;
    }

    public void setEuroToCents(EuroToCents euroToCents) {
        this.euroToCents = euroToCents;
    }

    public void setWebDriverWait(WebDriverWait wait) {
        this.wait = wait;
    }
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setHelpRequest(HelpRequest helpRequest) {
        this.helpRequest = helpRequest;
    }
    public void setMakeRequest(MakeRequest makeRequest) {
        this.makeRequest = makeRequest;
    }

    public void setMaltaPark(MaltaPark maltapark) {
        this.maltapark = maltapark;
    }

    //method to upload n number of alerts to marketalert
    public void scrapeAndUploadNAlertsWithWordSearch(int n, String search){
        screenscraper.visitWebsite(chosenUrl);
        screenscraper.closePopup();
        screenscraper.searchItemByWordSearch(search);
        screenscraper.scrapeFirstNResults(n);
        screenscraper.uploadItemListToMarketAlert();
        screenscraper.stop();
    }
    // remove alerts
    public void removeAlerts() {
        helpRequest.deletePost();
    }
}
