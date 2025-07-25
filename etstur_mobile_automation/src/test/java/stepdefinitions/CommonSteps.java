package stepdefinitions;

import utils.DateUtils;
import utils.TestContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.Given;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.ElementReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    DateUtils dateUtils = new DateUtils();

    @Given("enter {string} into the {string} field")
    public void enterTextIntoField(String text, String fieldKey) {
        By locator = ElementReader.getLocator(fieldKey);
        WebElement field = DriverManager.getDriver().findElement(locator);
        field.clear(); // optional: field temizlenebilir
        field.sendKeys(text);
    }

    @Given("tap the {string}")
    public void tapElement(String elementKey) {
        By locator = ElementReader.getLocator(elementKey);
        DriverManager.getDriver().findElement(locator).click();
    }

    @Given("verify that the {string} is displayed")
    public void verifyElementIsDisplayed(String elementKey) {
        By locator = ElementReader.getLocator(elementKey);
        boolean visible = DriverManager.getDriver().findElement(locator).isDisplayed();
        if (!visible) {
            throw new AssertionError(elementKey + " is not visible!");
        }
    }

    @Given("swipe to {string}")
    public void swipeToElement(String elementKey) {
        By locator = ElementReader.getLocator(elementKey);
        AppiumDriver driver = DriverManager.getDriver();

        int maxSwipes = 5;
        int swipeCount = 0;

        while (swipeCount < maxSwipes) {
            try {
                if (driver.findElement(locator).isDisplayed()) {
                    return;
                }
            } catch (Exception ignored) {}

            Dimension size = driver.manage().window().getSize();
            int startY = (int) (size.height * 0.7);
            int endY = (int) (size.height * 0.3);
            int centerX = size.width / 2;

            new TouchAction<>((PerformsTouchActions) driver)
                    .press(PointOption.point(centerX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(centerX, endY))
                    .release()
                    .perform();

            swipeCount++;
        }

        throw new AssertionError("Element '" + elementKey + "' not found after swiping.");
    }

    @Given("assert that the {string} contains text {string}")
    public void assertElementContainsText(String elementKey, String expectedText) {
        By locator = ElementReader.getLocator(elementKey);
        String actualText = DriverManager.getDriver().findElement(locator).getText();

        if (!actualText.contains(expectedText)) {
            throw new AssertionError("Expected text '" + expectedText + "' not found in '" + actualText + "'");
        }
    }

    @Given("assert saved {string} is equal to this {string}")
    public void assertStringsEqual(String expected, String actual) {
        assertThat(TestContext.get(actual))
                .withFailMessage("Expected <%s> but found <%s>", expected, actual)
                .isEqualTo(TestContext.get(expected));
    }
    @Given("take screenshot named {string}")
    public void takeScreenshot(String name) {
        File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        String filePath = "screenshots/" + name + "_" + System.currentTimeMillis() + ".png";

        try {
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(srcFile.toPath(), Paths.get(filePath));
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + filePath, e);
        }
    }

    @Given("wait for {string} to be visible")
    public void waitForElementToBeVisible(String elementKey) {
        By locator = ElementReader.getLocator(elementKey);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    @Given("Save {string} to {string}")
    public void saveElementTextToVariable(String elementKey, String variableName) {
        if (variableName.toLowerCase().startsWith("the ")) {
            variableName = variableName.substring(4);
        }

        By locator = ElementReader.getLocator(elementKey);
        String text = DriverManager.getDriver().findElement(locator).getText();

        TestContext.put(variableName, text);
        System.out.println("Saved text '" + text + "' to variable '" + variableName + "'");
    }

    @Given("select the date from {string} that is {int} days from today")
    public void selectPlusDaysFromToday(String element, int plusDay){
        String targetDay = DateUtils.getDayofMonth(plusDay);
        String baseXpath = ElementReader.getValueOfElement(element);
        String dynamicXPath = baseXpath + "[" + targetDay + "]";
        DriverManager.getDriver().findElement(By.xpath(dynamicXPath)).click();
    }

    @Given("select {int} passenger from {string}")
    public void selectPassenger(int targetCount,String element){
        String countXpath = ElementReader.getValueOfElement("adult_count_text");
        String decreaseButton = ElementReader.getValueOfElement(element);
        int currrentCount = Integer.parseInt(DriverManager.getDriver().findElement(By.xpath(countXpath)).getText());
        while (currrentCount>targetCount){
            DriverManager.getDriver().findElement(By.xpath(decreaseButton)).click();
            currrentCount--;
        }
    }


}
