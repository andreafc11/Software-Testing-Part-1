package tests.requests;

import com.scraper.models.Item;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import com.scraper.utils.MarketAlertStatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class HelpRequestTests {
    HelpRequest helpRequest;
    MakeRequest makeRequest;
    MarketAlertStatusCode marketAlertStatusCode;

    @BeforeEach
    public void setup(){
        helpRequest = mock(HelpRequest.class);
        makeRequest = new MakeRequest();
        marketAlertStatusCode = mock(MarketAlertStatusCode.class);
    }

    @AfterEach
    public void teardown(){
        helpRequest = null;
    }

    @Test
    public void testPostBadRequest(){
        // Setup

        // Exercise
        int statusCode = makeRequest.makePostRequest();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, statusCode);
    }

    @Test
    public void testPostCreatedRequest(){
        // Setup (if any)
        int alertType = 6;
        String heading = "Jumper Windows 11 Laptop";
        String description = "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD";
        String url = "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth";
        String imageUrl = "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg";
        int priceInCents = 24999;

        Item item = new Item(alertType, heading, description, url, imageUrl, priceInCents);
        makeRequest.setJSONObject(item);

        // Exercise
        int statusCode = makeRequest.makePostRequest();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.CREATED, statusCode);

        // Teardown (if any)
        makeRequest.makeDeleteRequest();
    }


    @Test
    public void testMakeDeleteOKRequest(){
        // Setup (if any);

        // Exercise
        int statusCode = makeRequest.makeDeleteRequest() ;

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.OK,statusCode);


        // Teardown (if any)
    }
}
