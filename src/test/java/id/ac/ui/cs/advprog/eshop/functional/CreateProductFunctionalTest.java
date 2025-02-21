package id.ac.ui.cs.advprog.eshop.functional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) {
        // Exercise: Navigate to Create Product page
        driver.get(baseUrl + "/product/create");
        
        // Verify the create product page is displayed
        String createPageTitle = driver.getTitle();
        assertEquals("Create New Product", createPageTitle);

        // Exercise: Fill in the product details
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("100");
        submitButton.click();

        // Verify: Check if redirected to product list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/list"));

        // Verify: Check if the new product appears in the list
        WebElement productTable = driver.findElement(By.cssSelector("table"));
        String tableContent = productTable.getText();
        assertTrue(tableContent.contains("Test Product"));
        assertTrue(tableContent.contains("100"));
    }

    @Test
    void createProduct_emptyName_staysOnPage(ChromeDriver driver) {
        // Exercise: Navigate to Create Product page
        driver.get(baseUrl + "/product/create");

        // Exercise: Submit form with empty name
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        quantityInput.sendKeys("100");
        submitButton.click();

        // Wait for error message to appear (add this)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".text-danger")));

        // Verify: Still on create product page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));

        // Verify: Error message is displayed
        assertTrue(errorMessage.getText().contains("Product name is required"));
    }

    @Test
    void createProduct_negativeQuantity_staysOnPage(ChromeDriver driver) {
        // Exercise: Navigate to Create Product page
        driver.get(baseUrl + "/product/create");

        // Exercise: Fill in the product details with negative quantity
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("-1");
        submitButton.click();

        // Wait for error message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".text-danger")));

        // Verify: Still on create product page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));

        // Verify: Error message is displayed
        assertTrue(errorMessage.getText().contains("Product quantity must be greater than or equal to 0"));
    }

@Test
void createProduct_emptyQuantity_staysOnPage(ChromeDriver driver) {
    // Exercise: Navigate to Create Product page
    driver.get(baseUrl + "/product/create");

    // Exercise: Submit form with empty quantity
    WebElement nameInput = driver.findElement(By.id("nameInput"));
    WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

    nameInput.sendKeys("Test Product");
    submitButton.click();

    // Wait for error message to appear
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".text-danger")));

    // Verify: Still on create product page
    String currentUrl = driver.getCurrentUrl();
    assertTrue(currentUrl.contains("/product/create"));

    // Verify: Error message is displayed
    assertTrue(errorMessage.getText().contains("Product quantity is required"));
}
}