package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
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
    void createProduct_emptyName_staysOnPage(ChromeDriver driver) throws Exception {
        // Exercise: Navigate to Create Product page
        driver.get(baseUrl + "/product/create");

        // Exercise: Submit form with empty name
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        quantityInput.sendKeys("100");
        submitButton.click();

        // Verify: Still on create product page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));
    }
}