package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    
    @Mock
    private IdGeneratorService idGeneratorService;
    
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(idGeneratorService);
        // Make the stubbing lenient so it doesn't fail tests that don't use it
        lenient().when(idGeneratorService.generateId()).thenReturn("generated-id");
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test 
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821d6e9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
    
    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        productRepository.create(product);
        
        Product foundProduct = productRepository.findById("test-id");
        assertNotNull(foundProduct);
        assertEquals("test-id", foundProduct.getProductId());
        assertEquals("Test Product", foundProduct.getProductName());
    }
    
    @Test
    void testFindByIdNonExistent() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testUpdate() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Create updated product with same ID
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(200);
        
        // Perform update
        Product result = productRepository.update(updatedProduct);
        
        // Verify the update
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        
        // Verify through findAll
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateSecondProduct() {
        // Create first product
        Product product1 = new Product();
        product1.setProductId("26fc7ab5-8e80-44d7-9fdc-2a804e1ede9f");
        product1.setProductName("First Product");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        // Create second product
        Product product2 = new Product();
        product2.setProductId("b6104af2-8a6e-40b8-aec5-396cdae48471");
        product2.setProductName("Second Product");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        // Update second product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("b6104af2-8a6e-40b8-aec5-396cdae48471");
        updatedProduct.setProductName("Updated Second Product");
        updatedProduct.setProductQuantity(300);
        
        Product result = productRepository.update(updatedProduct);
        
        // Verify the update - Fix: use consistent method calls
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        
        // Verify first product remained unchanged
        Iterator<Product> productIterator = productRepository.findAll();
        Product firstProduct = productIterator.next();
        assertEquals("First Product", firstProduct.getProductName());
        assertEquals(100, firstProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Non Existent Product");
        nonExistentProduct.setProductQuantity(100);
        
        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Verify product exists
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        // Delete the product
        productRepository.delete(product.getProductId());

        // Verify product no longer exists
        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        // Delete non-existent product
        productRepository.delete("non-existent-id");
        
        // Verify no products exist
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
}