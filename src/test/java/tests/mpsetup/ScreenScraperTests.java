package tests.mpsetup;

import com.scraper.price.EuroToCents;
import com.scraper.models.Item;
import com.scraper.website.MaltaPark;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import com.scraper.mpsetup.ScreenScraper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ScreenScraperTests {
    WebDriver driver;
    WebDriverWait wait;
    EuroToCents euroToCents;
    HelpRequest helpRequest;
    MakeRequest makeRequest;
    ScreenScraper screenscraper;
    MaltaPark maltapark;
    WebDriver.Options mockWebOptions;
    WebDriver.Window mockWebWindow;
    List<Item> itemList;


    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/owner/Desktop/cpsassignment/chromedriver");
        driver = mock(ChromeDriver.class);
        wait = mock(WebDriverWait.class);

        euroToCents = mock(EuroToCents.class);
        itemList = mock(List.class);
        helpRequest = mock(HelpRequest.class);
        makeRequest = mock(MakeRequest.class);
        maltapark = mock(MaltaPark.class);
        screenscraper = new ScreenScraper(driver, wait, euroToCents, maltapark, itemList, helpRequest, makeRequest);
    }

    @AfterEach
    public void teardown() {
        // Teardown (if any)
    }

    public void setupOfMocksForVisitWebsite() {
        // Setup
        mockWebOptions = mock(WebDriver.Options.class);
        mockWebWindow = mock(WebDriver.Window.class);

        when(driver.manage()).thenReturn(mockWebOptions);
        when(mockWebOptions.window()).thenReturn(mockWebWindow);
        doNothing().when(mockWebWindow).maximize();
    }

    @Test
    public void testVisitWebsite() {
        //Setup
        setupOfMocksForVisitWebsite();

        //Exercise
        screenscraper.visitWebsite("https://www.maltapark.com/");

        //Verify
        Mockito.verify(driver, times(1)).get(anyString());

        //Teardown (if any)
    }

    @Test
    public void testClosePopup() {
        //Setup
        WebElement mockCloseButton = mock(WebElement.class);
        when(maltapark.getClosePopup()).thenReturn(mockCloseButton);

        //Exercise
        screenscraper.closePopup();

        //Verify
        Mockito.verify(mockCloseButton, times(1)).click();

        //Teardown (if any)
    }

    @Test
    public void testSearchItemByWordSearch() {
        //Setup
        WebElement mockSearchField = mock(WebElement.class);
        WebElement mockSearchButton = mock(WebElement.class);
        when(maltapark.getSearchField()).thenReturn(mockSearchField);
        when(maltapark.getSearchButton()).thenReturn(mockSearchButton);

        //Exercise
        screenscraper.searchItemByWordSearch("Football shoes");

        //Verify
        Mockito.verify(mockSearchButton, times(1)).submit();

        //Teardown (if any)
    }

    @Test
    public void testScrape5Items() {
        //Setup
        List<String> mockListOf5Links = new ArrayList<>();
        mockListOf5Links.add("1");
        mockListOf5Links.add("2");
        mockListOf5Links.add("3");
        mockListOf5Links.add("4");
        mockListOf5Links.add("5");
        when(maltapark.getFirstNItemsUrls(5)).thenReturn(mockListOf5Links);

        setupOfMocksForVisitWebsite();

        when(maltapark.getItemAlertType()).thenReturn(6);
        when(maltapark.getItemHeading()).thenReturn("Heading");
        when(maltapark.getItemDescription()).thenReturn("Description");
        when(maltapark.getItemUrl()).thenReturn("Url");
        when(maltapark.getItemImageUrl()).thenReturn("imageUrl");
        when(maltapark.getItemPriceInCents()).thenReturn(99);

        //Exercise
        screenscraper.scrapeFirstNResults(5);

        //Verify
        verify(itemList, times(5)).add(any()); // assert that 5 items were added

        //Teardown (if any)
    }

    @Test
    public void testUploadListToMarketAlert() {
        //Setup
        Item item = new Item(6, "Heading", "Description", "Url", "imageUrl", 77);

        itemList = new ArrayList<>();
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);
        screenscraper.setItemList(itemList);

        when(helpRequest.makePost()).thenReturn(201);

        //Exercise
        screenscraper.uploadItemListToMarketAlert();

        //Verify
        verify(makeRequest, times(5)).setJSONObject(any());
        verify(helpRequest, times(5)).setMakeRequest(any());
        verify(helpRequest, times(5)).makePost();

        Assertions.assertEquals(201, helpRequest.makePost());

        //Teardown (if any)
    }

    @Test
    public void testQuit() {
        //Setup

        //Exercise
        screenscraper.stop();

        //Verify
        Mockito.verify(driver, times(1)).quit();

        //Teardown (if any)
    }
}
