package tests.interfacing;

import com.scraper.price.EuroToCents;
import com.scraper.interfacing.Facade;
import com.scraper.models.Item;
import com.scraper.website.MaltaPark;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import com.scraper.mpsetup.ScreenScraper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FacadeTests {
    Facade facade;
    WebDriver driver;
    ScreenScraper screenscraper;
    WebDriverWait wait;
    EuroToCents euroToCents;
    List<Item> itemList;
    HelpRequest helpRequest;
    MakeRequest makeRequest;
    MaltaPark maltapark;

    @BeforeEach
    public void setup(){
        //initiate SUT
        facade = new Facade();

        //mocking DOCs
        driver = mock(WebDriver.class);
        wait = mock(WebDriverWait.class);
        euroToCents = mock(EuroToCents.class);
        itemList = mock(List.class);
        helpRequest = mock(HelpRequest.class);
        makeRequest = mock(MakeRequest.class);
        maltapark = mock(MaltaPark.class);
        screenscraper = mock(ScreenScraper.class);

        //inject mocks of DOCs
        facade.setWebDriver(driver);
        facade.setWebDriverWait(wait);
        facade.setEuroToCents(euroToCents);
        facade.setItemList(itemList);
        facade.setHelpRequest(helpRequest);
        facade.setMakeRequest(makeRequest);
        facade.setMaltaPark(maltapark);
        facade.setScreenScraper(screenscraper);
    }

    @AfterEach
    public void teardown(){
        facade = null;
    }

    @Test
    public void testScrapeAndUpload5iPhoneSearchResults(){
        // Setup
        doNothing().when(screenscraper).visitWebsite("https://www.maltapark.com/");
        doNothing().when(screenscraper).closePopup();
        doNothing().when(screenscraper).searchItemByWordSearch(anyString());
        doNothing().when(screenscraper).scrapeFirstNResults(5);
        doNothing().when(screenscraper).uploadItemListToMarketAlert();
        doNothing().when(screenscraper).stop();
        when(itemList.size()).thenReturn(5);

        // Exercise
        facade.scrapeAndUploadNAlertsWithWordSearch(5, "iPhone");

        // Verify
        //get num of calls of each method
        verify(screenscraper, times(1)).visitWebsite(anyString());
        verify(screenscraper, times(1)).closePopup();
        verify(screenscraper, times(1)).searchItemByWordSearch(anyString());
        verify(screenscraper, times(1)).scrapeFirstNResults(5);
        verify(screenscraper, times(1)).uploadItemListToMarketAlert();
        verify(screenscraper, times(1)).stop();
        //other assertions
        Assertions.assertEquals(5, itemList.size());

        // Teardown (if any)
    }
    @Test
    public void testClearAlerts(){
        // Setup
        doReturn(200).when(helpRequest).deletePost();

        // Exercise
        facade.removeAlerts();

        // Verify
        //get num of calls of each method
        verify(helpRequest, times(1)).deletePost();

        // Teardown (if any)
    }
}
