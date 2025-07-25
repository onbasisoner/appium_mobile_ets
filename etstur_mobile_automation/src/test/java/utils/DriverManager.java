package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;

public class DriverManager {

    private static AppiumDriver driver;

    public static AppiumDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void initDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:deviceName", "emulator-5554");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:appPackage", "com.etstur");
            caps.setCapability("appium:appActivity", "com.etstur.modules.common.main.MainActivity");
            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:autoGrantPermissions",true);
            caps.setCapability("appium:newCommandTimeout", 300);


            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Appium sunucusuna bağlanılamadı", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
