# Appium Cucumber Automation Project

## Project Overview

This project is a mobile automation framework built with **Java**, **Appium**, and **Cucumber** for testing Android applications. It follows the BDD (Behavior Driven Development) approach using Gherkin feature files and step definitions. The framework is designed for maintainability, scalability, and clear reporting with **Allure** integration.

---

## Folder Structure

src/
├── test/
│ ├── java/
│ │ ├── hooks/ # Setup and teardown hooks for Cucumber tests
│ │ ├── runners/ # Test runners and retry logic classes
│ │ ├── stepdefinitions/ # Step definitions implementing Gherkin steps
│ │ └── utils/ # Utility classes: DriverManager, ElementReader, TestContext, RetryRule, DateUtils
│ └── resources/
│ ├── elements/ # JSON files defining UI element locators (e.g., elements.json)
│ └── features/ # Feature files written in Gherkin syntax (e.g., case.feature)


- **hooks/**: Contains `Hooks.java` which manages Appium driver initialization and teardown before and after scenarios.
- **runners/**: Holds Cucumber runners (`TestRunner.java`, `RetryRunner.java`) to run all tests or rerun failed scenarios.
- **stepdefinitions/**: Implements test steps in `CommonSteps.java` corresponding to Gherkin scenarios.
- **utils/**: Contains helper classes:
    - `DriverManager` — initializes and manages the AppiumDriver lifecycle.
    - `ElementReader` — reads UI element locators from JSON and provides Selenium/Appium locators.
    - `TestContext` — stores and retrieves variables across steps.
    - `RetryRule` — retries failed tests automatically.
    - `DateUtils` — date utilities for dynamic date selection.
- **resources/elements/**: JSON files with locator definitions used by ElementReader.
- **resources/features/**: Feature files describing the test scenarios in Gherkin language.

---

## Setup and Prerequisites

- **Java JDK 21** or later
- **Maven 3.6+** for dependency management and build
- **Android Emulator or real device** with USB debugging enabled
- **Appium Server** installed and running (default at `http://127.0.0.1:4723/wd/hub`)
- **Android SDK** and platform tools properly configured
---
### Installing Appium
```
- Using npm:

bash
npm install -g appium
appium

Or download Appium Desktop from appium.io

Starting Appium Server
Run appium command or launch via Appium Desktop GUI.
```
---
### How to Run Tests
```
Start your Android emulator or connect your real device.

Make sure Appium server is running.

From the project root directory, execute:


mvn clean test
This runs all feature files specified in the runner class.

mvn clean test -Dcucumber.filter.tags=@pricetests
This runs only tagged scenarios

Failed scenarios are saved and can be rerun using RetryRunner.
```

---

### Browser and Device Configuration

- The DriverManager class configures the AndroidDriver with desired capabilities:

- Platform: Android

- Device Name: emulator-5554 (modify as needed)

- Automation Engine: UiAutomator2

- Target app package and activity set to ETSTUR app identifiers.

- Auto grants permissions and sets command timeouts.

- For testing on a real device, update deviceName capability in DriverManager.java.

---
### Logging and Reporting
- The project integrates Allure for detailed and readable test reports.

- Retry logic ensures flaky tests rerun automatically to improve stability.

---

### Test Scenario Example

- Example snippet from case.feature:

@HotelSearchTests
Feature: Hotel search and price verification in ETSTUR app

@pricetests
Scenario: Search for Istanbul hotels and verify price consistency
* wait for "etstur_logo" to be visible
* tap the "hotel_search"
* enter "İstanbul Otelleri" into the "hotel_search_textbox" field
* select the date from "day_choice" that is 1 days from today
* select the date from "day_choice" that is 2 days from today
* select 1 passenger from "decrease_person_adult"
* tap the "search_hotel_button"
* Save "first_hotel_price" to "the hotel price"
* assert saved "the hotel price" is equal to this "the detail price"

---
### Dependencies
Key dependencies managed by Maven:

- Appium Java Client 9.5.0

- Selenium Java 4.34.0

- Cucumber JVM 7.23.0

- JUnit 4.13.2

- Allure Cucumber JVM 2.29.1

- JSON library for parsing element locators

- AssertJ for fluent assertions

### Notes
- The project uses Java 21 features; ensure your environment supports it.

- Locators are maintained externally in JSON files for easier maintenance.

- Test retry mechanism reduces flakiness.

- Allure reports provide insights with logs.
