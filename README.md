# E-commerce Automation Testing

A UI test automation project for an e-commerce web application, built
with Java, Selenium WebDriver, TestNG, and Maven. The project is
organized using the Page Object Model (POM) and supports test data
stored in Excel.

## Tech Stack

-   **Java** --- programming language
-   **Selenium WebDriver** --- browser automation
-   **TestNG** --- test execution and test organization
-   **Maven** --- dependency and build management
-   **Apache POI** --- reading test data from Excel (if configured in
    `pom.xml`)
-   **Allure Report** --- test reporting (if configured in the project)

## Project Structure

``` text
automation_test/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       └── java/
│           ├── base/       # Shared test setup and WebDriver configuration
│           ├── pages/      # Page Object classes
│           ├── tests/      # Automated test cases
│           └── utils/      # Reusable utilities, such as Excel readers
├── testdata.xlsx           # Test data workbook
├── pom.xml                 # Maven dependencies and configuration
├── .gitignore
└── README.md
```

The exact folders and classes may vary depending on the current
implementation.

## Prerequisites

Before running the tests, install:

1.  A compatible **JDK** version.
2.  **Maven** (unless the project includes and uses the Maven Wrapper).
3.  A supported browser, such as Chrome.
4.  The browser driver setup required by the Selenium version used in
    the project.

## Setup

1.  Clone the repository:

    ``` bash
    git clone <YOUR_GITHUB_REPOSITORY_URL>
    cd ecommerce-automation-testing
    ```

2.  Open the project in IntelliJ IDEA or another Java IDE.

3.  Check `pom.xml` and make sure the required dependencies and
    test-runner configuration are present.

4.  Update any website URL, browser settings, or test-data paths
    required by the project.

5.  Make sure `testdata.xlsx` contains only sample data and is located
    where the Excel-reading utility expects it.

## Running the Tests

Run the tests with Maven:

``` bash
mvn test
```

If your project uses a specific TestNG suite XML file, run it using the
suite configuration documented in your project or configured in
`pom.xml`.

## Test Data

Test data is stored in `testdata.xlsx`. The workbook may contain
separate sheets for different scenarios, such as registration, login,
product search, cart, or checkout.

Use dummy test accounts only. Do not commit real passwords, personal
information, payment details, or other secrets to the repository.

## Test Reports

The project includes Allure-related files/configuration. If Allure is
configured in `pom.xml`, run the tests first and then generate or view
the report using the Allure commands appropriate for your local
installation.

Generated reports and temporary Allure files should generally not be
committed to GitHub. The repository may include screenshots of report
results when useful.

## Design Approach

-   **Page Object Model (POM):** keeps page interactions separate from
    test scenarios.
-   **Reusable test setup:** shared setup can be managed in the base
    test class.
-   **Data-driven testing:** test scenarios can read input data from
    Excel.
-   **Test reporting:** Allure can be used to make test results easier
    to review when configured.

## Notes

-   The commands above assume Maven and the test runner are configured
    in `pom.xml`.
-   Adjust the repository URL, Java version, browser configuration, and
    Allure commands to match the actual project setup.
-   Never commit generated build output such as `target/`, IDE-specific
    files, or secrets.
