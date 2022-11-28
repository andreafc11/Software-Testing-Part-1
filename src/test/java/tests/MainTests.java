package tests;

import com.scraper.Main;
import com.scraper.interfacing.Facade;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MainTests {

    @Test
    public void testMainMethod(){
        //Setup
        Facade facade = mock(Facade.class);
        Main.setFacade(facade);

        //Exercise
        Main.main(null);

        //Verify
        verify(facade, times(1)).scrapeAndUploadNAlertsWithWordSearch(anyInt(),anyString());

        //Teardown (if any)
    }
}
