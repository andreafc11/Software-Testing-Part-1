package test.marketalerts.pageobjects;

import com.scraper.website.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MarketAlerts extends PageObject {

    public MarketAlerts(WebDriver driver){
        super(driver);
    }
    public void goToLoginPage() {
        driver.manage().window().maximize();
        driver.get("https://www.marketalertum.com/Alerts/Login");
    }

    public String getLogInText() {
        return driver.findElement(By.xpath("//a[contains(@class,'nav-link') and contains(@class,'text-dark') and contains(.,'Log In')]")).getText();
    }

    public WebElement getUserIdField() {
        return driver.findElement(By.id("UserId"));
    }

    public String getUserIDText() {
        return driver.findElement(By.xpath("//form/b[contains(.,'User ID')]")).getText();
    }

    public WebElement getSubmit() {
        return driver.findElement(By.xpath("//input[@type='submit']")); //retrieving only button
    }

    public void inputUserId(String id) {
        getUserIdField().sendKeys(id);
    }

    public List<WebElement> getAlertsByXpath() {
        return driver.findElements(By.xpath("/html/body/div/main/table"));
    }

    public String getLatestAlertsForAndreaByXpath() {
        return driver.findElement(By.xpath("//main/h1[contains(.,'Latest alerts for Andrea Fenech Cesareo')]")).getText();
    }

    public List<String> getAllIconLinksByXpath() {
        List<String> iconLinks = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            iconLinks.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[1]/td/h4/img")).getAttribute("src"));
        }
        return iconLinks;
    }

    public List<String> getAllHeadingsByXpath() {
        List<String> headingsText = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            headingsText.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[1]/td/h4")).getText());
        }
        return headingsText;
    }

    public List<String> getAllDescriptionsByXpath() {
        List<String> descText = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            descText.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[3]/td")).getText());
        }
        return descText;
    }

    public List<String> getAllImagesByXpath() {
        List<String> images = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            images.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[2]/td/img")).getAttribute("src"));
        }
        return images;
    }

    public List<String> getAllPricesByXpath() {
        List<String> prices = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            prices.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[4]/td")).getText());
        }
        return prices;
    }
    public List<String> getUrlsByXpath() {
        List<String> links = new ArrayList<>(5);
        for (int i = 1; i <= getAlertsByXpath().size(); i++) {
            links.add(driver.findElement(By.xpath("//body/div[contains(@class,'container')]/main/table["+i+"]/tbody/tr[5]/td/a")).getAttribute("href"));
        }
        return links;
    }

    public void logOutFromMarketAlerts() {
        driver.get("https://www.marketalertum.com/Home/Logout");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getIconFileByXpath() {
        String fullIconURL = driver.findElement(By.xpath("/html/body/div/main/table[1]/tbody/tr[1]/td/h4/img")).getAttribute("src");
        String PNGorJPG;
        try{
            PNGorJPG = new URL(fullIconURL).getPath();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            PNGorJPG = "";
        }
        PNGorJPG = PNGorJPG.replace("/images/","");
        return PNGorJPG;
    }
}
