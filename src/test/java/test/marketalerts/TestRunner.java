package test.marketalerts;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features"
        //runs website.feature by reading methods created in WebsiteSteps.java
)
public class TestRunner {
}
