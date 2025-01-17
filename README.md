# Gauge Java Automation Framework

This project is a test automation framework built with Gauge and Java, supporting API, Web, and Mobile testing.

## Prerequisites

- Java JDK 8 or higher
- Maven
- Gauge (`brew install gauge`)
- Android device or emulator with SwagLabs app installed
- Chrome and Edge browsers
- Appium Server

## Project Structure

```
gauge-java-automation/
├── specs/                    # Gauge specification files
├── src/
│   └── test/
│       └── java/
│           ├── APITestImplementation.java    # API test implementation
│           ├── MobileTestImplementation.java # Mobile test implementation
│           └── WebTestImplementation.java    # Web test implementation
├── pom.xml                   # Maven dependencies
└── README.md                 # Project documentation
```

## Setup

1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Start Appium server
4. Connect Android device or start emulator
5. Make sure SwagLabs app is installed on the device

## Running Tests

### Run all tests
```bash
mvn clean compile test-compile gauge:execute
```

### Run specific test
```bash
mvn clean compile test-compile gauge:execute -DspecsDir=specs/api_test.spec  # For API tests
mvn clean compile test-compile gauge:execute -DspecsDir=specs/mobile_test.spec  # For Mobile tests
mvn clean compile test-compile gauge:execute -DspecsDir=specs/web_test.spec  # For Web tests
```

## Test Implementations

### API Testing
- Uses REST-assured for API testing
- Implements POST and GET requests
- Validates response status and content

### Mobile Testing
- Uses Appium for mobile automation
- Tests SwagLabs mobile app
- Implements login functionality
- Automatically closes app after tests

### Web Testing
- Uses Selenium WebDriver for web automation
- Supports both Chrome and Edge browsers
- Implements search functionality on Baidu
- Automatically closes browser after tests

## Configuration

### Mobile Test Configuration
```java
UiAutomator2Options options = new UiAutomator2Options()
    .setDeviceName("ZL5227R9TD")
    .setAutomationName("UiAutomator2")
    .setPlatformName("Android")
    .setPlatformVersion("10")
    .setNoReset(true)
    .setAppPackage("com.swaglabsmobileapp")
    .setAppActivity("com.swaglabsmobileapp.MainActivity");
```

### Web Test Configuration
- Uses WebDriverManager for automatic driver management
- Supports Chrome and Edge browsers
- Implements clean browser session management

## Reports

Test reports are generated in HTML format and can be found at:
```
/reports/html-report/index.html
```

## Best Practices

1. Each test implementation is separated into its own class
2. Proper cleanup is implemented for both mobile and web tests
3. Detailed logging is added for better debugging
4. Error handling is implemented for robust test execution

## Known Issues

- CDP (Chrome DevTools Protocol) compatibility warnings may appear during web tests
- These warnings don't affect test execution and can be safely ignored

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request
