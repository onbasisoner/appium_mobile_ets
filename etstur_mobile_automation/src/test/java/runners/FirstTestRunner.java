package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"pretty", "summary", "html:target/cucumber-html-report.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","rerun:runner/failed_scenarios.txt"},
        monochrome = true
)
public class FirstTestRunner {
}


