package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }
    
    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }
    
    @Test
    void testGetId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getId());
    }
    
    @Test
    void testGetName() {
        assertEquals("Sampo Cap Bambang", this.product.getName());
    }
    
    @Test
    void testGetQuantity() {
        assertEquals(100, this.product.getQuantity());
    }
    
    @Test
    void testSettersUpdatesInheritedFields() {
        this.product.setId("new-id");
        assertEquals("new-id", this.product.getId());
        assertEquals("new-id", this.product.getProductId());
        
        this.product.setName("New Name");
        assertEquals("New Name", this.product.getName());
        assertEquals("New Name", this.product.getProductName());
        
        this.product.setQuantity(200);
        assertEquals(200, this.product.getQuantity());
        assertEquals(200, this.product.getProductQuantity());
    }

    @Test
    void getQuantity_whenProductQuantityIsNull_shouldReturnZero() {
        // Create product with null quantity
        Product product = new Product();
        // Don't set productQuantity, so it stays null
        
        // Test the getQuantity method
        assertEquals(0, product.getQuantity());
    }
    
    @Test
    void getQuantity_whenProductQuantityIsNotNull_shouldReturnQuantity() {
        // Create product with non-null quantity
        Product product = new Product();
        product.setProductQuantity(10);
        
        // Test the getQuantity method
        assertEquals(10, product.getQuantity());
    }
}