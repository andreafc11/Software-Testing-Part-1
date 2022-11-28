package tests.requests;

import com.scraper.models.Item;
import com.scraper.requests.HelpRequest;
import com.scraper.requests.MakeRequest;
import com.scraper.utils.MarketAlertStatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MakeRequestTests {
    MakeRequest makeRequest;
    HelpRequest helpRequest;
    MarketAlertStatusCode marketAlertStatusCode;

    @BeforeEach
    public void setup(){
        // Setup
        helpRequest = new HelpRequest();
        makeRequest = mock(MakeRequest.class);
        helpRequest.setMakeRequest(makeRequest);
        marketAlertStatusCode = mock(MarketAlertStatusCode.class);
    }

    @AfterEach
    public void teardown(){
        helpRequest = null;
    }

    @Test
    public void testPostCreatedRequest(){
        // Setup (if any)
        Mockito.when(makeRequest.makePostRequest()).thenReturn(marketAlertStatusCode.CREATED);

        // create dummy object
        int alertType = 6;
        String heading = "Jumper Windows 11 Laptop";
        String description = "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD";
        String url = "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth";
        String image = "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg";
        int priceInCents = 24999;
        Item item = new Item(alertType, heading, description, url, image, priceInCents);

        // setter injection
        makeRequest.setJSONObject(item);

        // Exercise
        int statusCode = makeRequest.makePostRequest();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.CREATED, statusCode);
        verify(makeRequest, times(1)).makePostRequest();

        // Teardown (if any)
    }

    @Test
    public void testPostBadRequest(){
        // Setup (if any)
        doReturn(marketAlertStatusCode.BAD_REQUEST).when(makeRequest).makePostRequest();

        // Exercise
        int response = makeRequest.makePostRequest();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, response);
        verify(makeRequest, times(1)).makePostRequest();

        // Teardown (if any)
    }

    @Test
    public void testPostUnsupportedMediaTypeRequest(){
        // Setup (if any)
        Mockito.when(makeRequest.makePostRequest()).thenReturn(marketAlertStatusCode.UNSUPPORTED_MEDIA_TYPE);

        // Exercise
        int response = makeRequest.makePostRequest();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.UNSUPPORTED_MEDIA_TYPE, response);
        verify(makeRequest, times(1)).makePostRequest();

        // Teardown (if any)
    }
    @Test
    public void testPostServiceUnavailableRequest(){
        // Setup (if any)
        helpRequest.setMakeRequest(null);

        // Exercise
        int statusCode = helpRequest.makePost();

        // Verify
        Assertions.assertEquals(MarketAlertStatusCode.SERVICE_UNAVAILABLE, statusCode);

        // Teardown (if any)
    }
    @Test
    public void testPostSuccessfulRequestBy3rdTime(){
        // Setup (if any)
        when(makeRequest.makePostRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.CREATED);

        // create dummy object
        int alertType = 6;
        String heading = "Jumper Windows 11 Laptop";
        String description = "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD";
        String url = "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth";
        String imageUrl = "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg";
        int priceInCents = 24999;
        Item item = new Item(alertType, heading, description, url, imageUrl, priceInCents);

        // setter injection
        makeRequest.setJSONObject(item);

        // Exercise
        int statusCode = helpRequest.makePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.CREATED, statusCode);
        verify(makeRequest, times(3)).makePostRequest();

        // Teardown (if any)
    }
    @Test
    public void testPostUnsuccessfulRequestBy3rdTime(){
        // Setup (if any)
        when(makeRequest.makePostRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST);

        // create dummy object
        int alertType = 6;
        String heading = "Jumper Windows 11 Laptop";
        String description = "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD";
        String url = "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth";
        String imageUrl = "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg";
        int priceInCents = 24999;
        Item item = new Item(alertType, heading, description, url, imageUrl, priceInCents);

        // setter injection
        makeRequest.setJSONObject(item);

        // Exercise
        int statusCode = helpRequest.makePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, statusCode);
        verify(makeRequest, times(3)).makePostRequest();

        // Teardown (if any)
    }

    @Test
    public void testMaximumAttemptsToUpload(){
        // Setup (if any)
        when(makeRequest.makePostRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST, marketAlertStatusCode.CREATED);

        // Exercise
        int statusCode = helpRequest.makePost();

        // Verify
        verify(makeRequest, times(3)).makePostRequest();
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, statusCode);

        // Teardown (if any)
    }

    @Test
    public void testDeleteOKRequest(){
        // Setup (if any)
        Mockito.when(makeRequest.makeDeleteRequest()).thenReturn(MarketAlertStatusCode.OK);

        // Exercise
        int statusCode = makeRequest.makeDeleteRequest() ;

        // Verify
        Assertions.assertEquals(MarketAlertStatusCode.OK,statusCode);
        verify(makeRequest, times(1)).makeDeleteRequest();

        // Teardown (if any)
    }

    @Test
    public void testDeleteServiceUnavailableRequest(){
        // Setup (if any)
        helpRequest.setMakeRequest(null);

        // Exercise
        int statusCode = helpRequest.deletePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.SERVICE_UNAVAILABLE, statusCode);

        // Teardown (if any)
    }
    @Test
    public void testDeleteSuccessfulRequestBy3rdTime(){
        // Setup (if any)
        when(makeRequest.makeDeleteRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.OK);

        // Exercise
        int statusCode = helpRequest.deletePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.OK, statusCode);
        verify(makeRequest, times(3)).makeDeleteRequest();

        // Teardown (if any)
    }
    @Test
    public void testDeleteUnsuccessfulRequestBy3rdTime(){
        // Setup (if any)
        when(makeRequest.makeDeleteRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST);

        // Exercise
        int statusCode = helpRequest.deletePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, statusCode);
        verify(makeRequest, times(3)).makeDeleteRequest();

        // Teardown (if any)
    }
    @Test
    public void testMaximumAttemptsToDelete(){
        // Setup (if any)
        when(makeRequest.makeDeleteRequest()).thenReturn(marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST,marketAlertStatusCode.BAD_REQUEST, marketAlertStatusCode.OK);

        // Exercise
        int statusCode = helpRequest.deletePost();

        // Verify
        Assertions.assertEquals(marketAlertStatusCode.BAD_REQUEST, statusCode);
        verify(makeRequest, times(3)).makeDeleteRequest();

        // Teardown (if any)
    }

}
