package test.marketalerts;

import com.scraper.interfacing.Facade;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import test.marketalerts.pageobjects.MarketAlerts;

import java.util.List;

public class WebsiteSteps {

    // protected methods
    protected MarketAlerts marketalerts;
    protected Facade screenScraperFacade;
    protected WebDriver driver;

    // private methods which call upon marketalert methods
    private void assertThatLoginPageDetailsHaveLoaded(){
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/Login", marketalerts.getCurrentUrl());
        Assertions.assertEquals("- MarketAlertUM", marketalerts.getTitle());// this appears to be the name of the website
        Assertions.assertEquals("Log In", marketalerts.getLogInText()); // asserting the existence of tab including "Log In" text
        Assertions.assertEquals("User ID:", marketalerts.getUserIDText()); // asserting the existence of tab including "User ID:" text
        Assertions.assertNotNull(marketalerts.getUserIdField()); // asserting the User ID input box is present
        Assertions.assertNotNull(marketalerts.getSubmit()); //asserting the submit button is present
    }

    private void assertThatMyAlertsHaveLoaded(){
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/List", marketalerts.getCurrentUrl());
        Assertions.assertEquals("Latest alerts for Andrea Fenech Cesareo", marketalerts.getLatestAlertsForAndreaByXpath());
    }

    private void logIn(){
        String userValidUserId = "94817701-f7f3-4f5d-9419-c6da1b8d6d84";
        marketalerts.inputUserId(userValidUserId);
        marketalerts.getSubmit().submit();
        assertThatMyAlertsHaveLoaded();
    }

    private void uploadNAlerts(int n) {
        uploadNAlerts(n, "iPhone");
    }

    private void uploadNAlerts(int n, String productSearch) {
        screenScraperFacade = new Facade();
        screenScraperFacade.removeAlerts();

        screenScraperFacade.scrapeAndUploadNAlertsWithWordSearch(n, productSearch);
    }
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "/Users/owner/Desktop/cpsassignment/chromedriver");
        driver = new ChromeDriver();
        marketalerts = new MarketAlerts(driver);
    }
    @After
    public void teardown(){
        marketalerts.logOutFromMarketAlerts();
        driver.quit();
    }

    // public methods
    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        marketalerts.goToLoginPage();
        assertThatLoginPageDetailsHaveLoaded();
    }

    @When("I login using valid credentials")
    public void iLoginWithValidCredentials() {
        logIn();
    }
    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        assertThatMyAlertsHaveLoaded();
    }

    @When("I login using invalid credentials")
    public void iLoginWithInvalidCredentials() {
        String userInvalidUserId = "Rafael Leão";
        marketalerts.inputUserId(userInvalidUserId);
        marketalerts.getSubmit().submit();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        assertThatLoginPageDetailsHaveLoaded();
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int n) {
        uploadNAlerts(n);
    }
    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        logIn();
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        assertThatMyAlertsHaveLoaded();
        Assertions.assertTrue( 1 <= alertsListSize && alertsListSize <= 5);
    }
    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> allIconLinks = marketalerts.getAllIconLinksByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getAllIconLinksByXpath().size());
        for (String element: allIconLinks) {
            Assertions.assertNotNull(element);
        }
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> allHeadings = marketalerts.getAllHeadingsByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getAllHeadingsByXpath().size());
        for (String element: allHeadings) {
            Assertions.assertNotNull(element);
        }
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> allDescriptions = marketalerts.getAllDescriptionsByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getAllHeadingsByXpath().size());
        for (String element: allDescriptions) {
            Assertions.assertNotNull(element);
        }
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> allImageSources = marketalerts.getAllImagesByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getAllIconLinksByXpath().size());
        for (String element: allImageSources) {
            Assertions.assertNotNull(element);
        }
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> allPrices = marketalerts.getAllPricesByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getAllPricesByXpath().size());
        for (String element: allPrices) {
            Assertions.assertNotNull(element);
        }
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        int alertsListSize = marketalerts.getAlertsByXpath().size();
        List<String> urls = marketalerts.getUrlsByXpath();
        Assertions.assertEquals(alertsListSize, marketalerts.getUrlsByXpath().size());
        for (String element: urls) {
            Assertions.assertNotNull(element);
        }
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int n) {
        uploadNAlerts(n+1);
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int amountExpected) {
        Assertions.assertEquals(amountExpected, marketalerts.getAlertsByXpath().size());
    }
    @Given("I am an administrator of the website and I upload an alert of type 1")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType1() {
        uploadNAlerts(1, "Renault");
    }

    @Given("I am an administrator of the website and I upload an alert of type 2")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType2() {
        uploadNAlerts(1, "Marine");
    }

    @Given("I am an administrator of the website and I upload an alert of type 3")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType3() {
        uploadNAlerts(1, "Long Lets");
    }

    @Given("I am an administrator of the website and I upload an alert of type 4")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType4() {
        uploadNAlerts(1, "Property For Sale");
    }

    @Given("I am an administrator of the website and I upload an alert of type 5")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType5() {
        uploadNAlerts(1, "Toys");
    }

    @Given("I am an administrator of the website and I upload an alert of type 6")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType6() {
        uploadNAlerts(1, "Video Games");
    }
    @And("the icon displayed should be {string}")
    public void theIconDisplayedShouldBe(String icon_File) {
        Assertions.assertEquals(icon_File, marketalerts.getIconFileByXpath());
    }

}
