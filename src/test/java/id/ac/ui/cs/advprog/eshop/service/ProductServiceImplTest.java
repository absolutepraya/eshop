package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepositoryBean;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void create_shouldCreateProduct() {
        when(productRepositoryBean.create(any(Product.class))).thenReturn(product);

        Product created = productService.create(product);

        verify(productRepositoryBean, times(1)).create(product);
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

        when(productRepositoryBean.findAll()).thenReturn(productList.iterator());

        List<Product> found = productService.findAll();

        verify(productRepositoryBean, times(1)).findAll();
        assertEquals(2, found.size());
        assertEquals(product.getProductId(), found.get(0).getProductId());
        assertEquals(product2.getProductId(), found.get(1).getProductId());
    }

    @Test
    void findAll_whenEmpty_shouldReturnEmptyList() {
        when(productRepositoryBean.findAll()).thenReturn(new ArrayList<Product>().iterator());

        List<Product> found = productService.findAll();

        verify(productRepositoryBean, times(1)).findAll();
        assertTrue(found.isEmpty());
    }

@Test
    void edit_whenProductExists_shouldUpdateProduct() {
        // Create initial product in the list
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepositoryBean.findAll()).thenReturn(productList.iterator());
        
        // Create updated product
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);
        
        when(productRepositoryBean.edit(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.edit(updatedProduct);

        verify(productRepositoryBean).findAll();
        verify(productRepositoryBean).edit(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void edit_whenProductDoesNotExist_shouldReturnNull() {
        // Empty product list
        when(productRepositoryBean.findAll()).thenReturn(new ArrayList<Product>().iterator());
        
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Non Existent");
        nonExistentProduct.setProductQuantity(1);

        Product result = productService.edit(nonExistentProduct);

        verify(productRepositoryBean).findAll();
        verify(productRepositoryBean, never()).edit(any(Product.class));
        assertNull(result);
    }

    @Test
    void edit_whenMultipleProducts_shouldUpdateCorrectOne() {
        // Create list with multiple products
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        
        Product product2 = new Product();
        product2.setProductId("second-id");
        product2.setProductName("Second Product");
        product2.setProductQuantity(50);
        productList.add(product2);
        
        when(productRepositoryBean.findAll()).thenReturn(productList.iterator());
        
        // Update second product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("second-id");
        updatedProduct.setProductName("Updated Second Product");
        updatedProduct.setProductQuantity(75);
        
        when(productRepositoryBean.edit(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.edit(updatedProduct);

        verify(productRepositoryBean, times(1)).edit(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void delete_shouldDeleteProduct() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        
        productService.delete(productId);

        verify(productRepositoryBean, times(1)).delete(productId);
    }
}