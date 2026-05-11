# BankBot - Selenium Java Automation Framework

## Problem Statement
Design and develop a Selenium-Java Test Automation Framework for a live demo online banking web application (Guru99 Bank).

## Application Under Test
URL: https://demo.guru99.com/V4/index.php

## Tools Used
- Java 17
- Selenium WebDriver 4.15.0
- TestNG 7.10.2
- WebDriverManager 5.6.3
- ExtentReports 5.1.1
- Maven

## Project Structure
```
src/main/resources/
    config.properties          - URL, browser, credentials, timeout

src/test/java/com/krce/
    base/
        BaseTest.java          - Browser setup and teardown
        BasePage.java          - Common wait and action methods

    pages/
        LoginPage.java         - Login page actions
        ManagerPage.java       - Dashboard menu navigation
        NewCustomerPage.java   - Add customer form
        EditCustomerPage.java  - Edit customer form
        NewAccountPage.java    - Create account form
        EditAccountPage.java   - Edit account form
        FundTransferPage.java  - Fund transfer form

    tests/
        LoginTest.java         - Module 1: Login tests
        CustomerTest.java      - Module 2: Customer management
        AccountTest.java       - Module 3: Account management
        FundTransferTest.java  - Module 4: Fund transfer
        ValidationTest.java    - Module 5: Form validations

    utils/
        ConfigReader.java      - Reads config.properties
        ScreenshotUtils.java   - Screenshot on failure
        TestListener.java      - ITestListener for reports
        ExtentReportManager.java - HTML report setup

src/test/resources/
    testng.xml                 - Test suite configuration
```

## Test Modules Covered

### Module 1 - User Authentication
- Valid manager login
- Invalid login with DataProvider
- Blank fields login
- Logout redirect

### Module 2 - Customer Management
- Create new customer
- Edit customer address
- Duplicate email error

### Module 3 - Account Management
- Create Savings account
- Create second account
- Invalid customer ID error

### Module 4 - Fund Transfer
- Valid fund transfer between accounts
- Invalid payee account error

### Module 5 - Form Validations
- Empty name field error
- Empty city field error
- Non-numeric PIN error
- Non-numeric mobile error
- Invalid email format error

## How to Run
```
mvn clean test
```

## Report Path
```
reports/ExtentReport.html
```

## Screenshot Path
```
screenshots/
```

## Challenges Faced
- Guru99 Bank uses JavaScript alerts for error messages, handled using explicit waits
- Dynamic elements needed WebDriverWait instead of Thread.sleep
- Unique email needed for each customer creation to avoid duplicate errors
- Test data sharing between modules using static variables
