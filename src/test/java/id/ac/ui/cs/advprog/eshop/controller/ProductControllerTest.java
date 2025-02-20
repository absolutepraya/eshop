package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void createProductPage_shouldReturnCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("createProduct"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_whenValidProduct_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/product/create")
               .param("productName", "Test Product")
               .param("productQuantity", "100"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:list"));
        
        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void createProductPost_whenInvalidProduct_shouldReturnCreatePage() throws Exception {
        mockMvc.perform(post("/product/create")
               .param("productName", "") // Invalid: empty name
               .param("productQuantity", "100"))
               .andExpect(status().isOk())
               .andExpect(view().name("createProduct"));
        
        verify(service, never()).create(any(Product.class));
    }

    @Test
    void productListPage_shouldReturnProductListPage() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(service.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("productList"))
               .andExpect(model().attribute("products", products));
    }

    @Test
    void editProductPage_withMultipleProducts_shouldReturnEditPage() throws Exception {
        // Create multiple products to test loop iteration
        Product product1 = new Product();
        product1.setProductId("first-id");
        product1.setProductName("First Product");
        
        Product product2 = new Product();
        product2.setProductId("test-id");
        product2.setProductName("Test Product");
        
        List<Product> products = Arrays.asList(product1, product2);
        when(service.findAll()).thenReturn(products);

        // Test finding the second product
        mockMvc.perform(get("/product/edit")
               .param("id", "test-id"))
               .andExpect(status().isOk())
               .andExpect(view().name("editProduct"))
               .andExpect(model().attributeExists("product"))
               .andExpect(model().attribute("product", product2));
    }

    @Test
    void editProductPage_whenProductIsFirst_shouldReturnEditPage() throws Exception {
        // Test finding the first product in the list
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        
        List<Product> products = Arrays.asList(product);
        when(service.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/edit")
               .param("id", "test-id"))
               .andExpect(status().isOk())
               .andExpect(view().name("editProduct"))
               .andExpect(model().attributeExists("product"))
               .andExpect(model().attribute("product", product));
    }

    @Test
    void editProductPage_withNonExistentId_shouldRedirectToList() throws Exception {
        // Create a list with some products but not the one we're looking for
        Product product1 = new Product();
        product1.setProductId("other-id");
        Product product2 = new Product();
        product2.setProductId("another-id");
        
        List<Product> products = Arrays.asList(product1, product2);
        when(service.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/edit")
               .param("id", "non-existent-id"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:list"));
    }

    @Test
    void editProductPage_whenProductListEmpty_shouldRedirectToList() throws Exception {
        // Test with empty product list
        when(service.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/product/edit")
               .param("id", "any-id"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:list"));
    }

    @Test
    void editProductPost_whenValidProduct_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/product/edit")
               .param("productId", "test-id")
               .param("productName", "Updated Product")
               .param("productQuantity", "200"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:list"));
        
        verify(service, times(1)).edit(any(Product.class));
    }

    @Test
    void editProductPost_whenInvalidProduct_shouldReturnEditPage() throws Exception {
        mockMvc.perform(post("/product/edit")
               .param("productId", "test-id")
               .param("productName", "") // Invalid: empty name
               .param("productQuantity", "200"))
               .andExpect(status().isOk())
               .andExpect(view().name("editProduct"));
        
        verify(service, never()).edit(any(Product.class));
    }

    @Test
    void deleteProduct_shouldRedirectToList() throws Exception {
        String productId = "test-id";
        
        mockMvc.perform(get("/product/delete")
               .param("id", productId))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:list"));
        
        verify(service, times(1)).delete(productId);
    }
}