package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@runner/failed_scenarios.txt",
        glue = "stepdefinitions",
        plugin = {"pretty", "html:target/rerun-report.html"},
        monochrome = true
)
public class RetryRunner {

    @BeforeClass
    public static void checkRerunFileExists() {
        File rerunFile = new File("runner/failed_scenarios.txt");
        Assume.assumeTrue("Rerun file yok, RetryRunner atlanıyor", rerunFile.exists());
    }
}
