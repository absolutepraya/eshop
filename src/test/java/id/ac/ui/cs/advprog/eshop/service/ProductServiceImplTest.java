package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ItemRepository<Product> productRepository;

    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
        
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void create_shouldCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product created = productService.create(product);

        verify(productRepository, times(1)).create(product);
        assertEquals(product.getProductId(), created.getProductId());
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821d6e9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> found = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertEquals(2, found.size());
        assertEquals(product.getProductId(), found.get(0).getProductId());
        assertEquals(product2.getProductId(), found.get(1).getProductId());
    }

    @Test
    void findAll_whenEmpty_shouldReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>().iterator());

        List<Product> found = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertTrue(found.isEmpty());
    }
    
    @Test
    void findById_shouldReturnCorrectProduct() {
        when(productRepository.findById(product.getId())).thenReturn(product);
        
        Product found = productService.findById(product.getId());
        
        verify(productRepository, times(1)).findById(product.getId());
        assertEquals(product.getProductId(), found.getProductId());
        assertEquals(product.getProductName(), found.getProductName());
    }
    
    @Test
    void findById_whenNonExistent_shouldReturnNull() {
        when(productRepository.findById("non-existent")).thenReturn(null);
        
        Product found = productService.findById("non-existent");
        
        verify(productRepository, times(1)).findById("non-existent");
        assertNull(found);
    }

    @Test
    void update_shouldUpdateProduct() {
        // Create updated product
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);
        
        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        verify(productRepository).update(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void update_whenNonExistent_shouldReturnNull() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Non Existent");
        nonExistentProduct.setProductQuantity(1);
        
        when(productRepository.update(nonExistentProduct)).thenReturn(null);

        Product result = productService.update(nonExistentProduct);

        verify(productRepository).update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void delete_shouldDeleteProduct() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        
        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }
}