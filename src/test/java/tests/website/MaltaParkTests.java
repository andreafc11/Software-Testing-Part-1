package tests.website;

import com.scraper.price.EuroToCents;
import com.scraper.website.MaltaPark;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MaltaParkTests {
    WebDriver driver;
    WebDriverWait wait;
    WebElement mockWebElement;
    EuroToCents euroToCents;
    MaltaPark maltapark;

    @BeforeEach
    public void setup(){
        //Setup before each test case
        driver = mock(ChromeDriver.class);
        wait = mock(WebDriverWait.class);
        euroToCents = mock(EuroToCents.class);
        maltapark = new MaltaPark(driver, wait, euroToCents);
        mockWebElement = mock(WebElement.class);

        Mockito.when(wait.until(any())).thenReturn(true);
        Mockito.when(driver.findElement(Mockito.any())).thenReturn(mockWebElement);

    }

    @AfterEach
    public void teardown(){
        //Teardown before each test case
        maltapark = null;
    }

    @Test
    public void testGetSearchField(){
        //Setup (if any)
        Mockito.when(mockWebElement.getText()).thenReturn(Mockito.anyString());

        //Exercise
        WebElement searchFieldElement = maltapark.getSearchField();

        //Verify
        Assertions.assertNotNull(searchFieldElement);

        //Teardown (if any)
    }

    @Test
    public void testSearchButton(){
        //Setup (if any)
        Mockito.when(mockWebElement.getText()).thenReturn(Mockito.anyString());

        //Exercise
        WebElement searchButtonElement = maltapark.getSearchButton();

        //Verify
        Assertions.assertNotNull(searchButtonElement);

        //Teardown (if any)
    }

    @Test
    public void testGetItemHeading(){
        //Setup (if any)
        Mockito.when(mockWebElement.getText()).thenReturn(Mockito.anyString());

        //Exercise
        String itemHeadingString = maltapark.getItemHeading();

        //Verify
        Assertions.assertNotNull(itemHeadingString);

        //Teardown (if any)
    }
    @Test
    public void testGetItemDescription(){
        //Setup (if any)
        Mockito.when(mockWebElement.getText()).thenReturn(Mockito.anyString());

        //Exercise
        String itemDescriptionString = maltapark.getItemDescription();

        //Verify
        Assertions.assertNotNull(itemDescriptionString);

        //Teardown (if any)
    }

    @Test
    public void testGetItemUrl(){
        //Setup (if any)

        //Exercise
        maltapark.getItemUrl();

        //Verify
        Mockito.verify(driver, Mockito.times(1)).getCurrentUrl();

        //Teardown (if any)
    }


    @Test
    public void testGetItemImage(){
        //Setup (if any)
        Mockito.when(mockWebElement.getAttribute(anyString())).thenReturn(Mockito.anyString());

        //Exercise
        String itemImageString = maltapark.getItemImage();

        //Verify
        Assertions.assertNotNull(itemImageString);

        //Teardown (if any)
    }
    @Test
    public void testGetItemPriceInCents(){
        //Setup (if any)
        int cents = 214351;
        doReturn(String.valueOf(cents)).when(mockWebElement).getText();
        doReturn(cents).when(euroToCents).convert(anyString());

        //Exercise
        int itemPriceInCents = maltapark.getItemPriceInCents();

        //Verify
        Assertions.assertEquals(cents, itemPriceInCents);

        //Teardown (if any)
    }

    @Test
    public void testGetCategoryOfItem(){
        //Setup
        //Dummy data passed
        Mockito.when(mockWebElement.getText()).thenReturn("Category:Football");

        //Exercise
        String category = maltapark.getCategoryOfItem();

        //Verify
        Assertions.assertEquals("Football",category);

        //Teardown (if any)
    }

    @Test
    public void testCorrectAlertType(){
        //Setup
        Mockito.when(mockWebElement.getText()).thenReturn(
                "Category:Cars","Category:Scooters", "Category:Quad Bikes",//1,1,1
                "Category:Marine",//2
                "Category:Long Lets","Category:Short / Holiday Lets",//3,3
                "Category:Property For Sale",//4
                "Category:Toys", "Category:Dolls & Bears",//5
                "Category:Cameras & Photo", "Category:Video Games", "Category:Computers & Office",//6,6,6
                "Category:Unlisted Category");//-1
        int [] correctAlertTypes = new int[]{
          1,1,1,2,3,3,4,5,5,6,6,6,-1
        };
        int [] testingAlertTypes = new int[13];

        //Exercise
        //iterate multiple times to check if the correct alert type was assigned at the partition ends
        for(int t = 0; t < 13; t++){
            testingAlertTypes[t] = maltapark.getItemAlertType();
        }

        //Verify
        for(int t = 0; t < 13; t++){
            Assertions.assertEquals(correctAlertTypes[t],testingAlertTypes[t]);
        }

        //Teardown
    }

    @Test
    public void testGetFirst5ItemUrls(){
        //Setup
        List<WebElement> mockListLinks = mock(List.class);

        Mockito.when(driver.findElements(any())).thenReturn(mockListLinks);
        Mockito.when(mockWebElement.getAttribute(anyString())).thenReturn(Mockito.anyString());

        //Exercise
        List<String> links = maltapark.getFirstNItemsUrls(5);

        //Verify
        Assertions.assertNotNull(links);

        //Teardown (if any)
    }

}
