package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ProductService productService;

    private Product product1;
    private Product product2;
    private Order testOrder;
    private Payment testPayment;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        // Setup products
        product1 = new Product();
        product1.setId("prod-1");
        product1.setName("Product 1");
        product1.setQuantity(10);

        product2 = new Product();
        product2.setId("prod-2");
        product2.setName("Product 2");
        product2.setQuantity(20);

        products = Arrays.asList(product1, product2);

        // Setup test order
        List<Product> orderProducts = new ArrayList<>();
        orderProducts.add(product1);
        testOrder = new Order("order-1", orderProducts, System.currentTimeMillis(), "testAuthor");

        // Setup test payment
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("data", "value");
        testPayment = new Payment("payment-1", testOrder, "VOUCHER", paymentData);
    }

    @Test
    void orderHomePage_shouldReturnOrderHomePage() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHome"));
    }

    @Test
    void createOrderPage_shouldReturnCreateOrderPageWithProducts() throws Exception {
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products));
    }

    @Test
    void createOrder_withNoSelectedProducts_shouldReturnErrorMessage() throws Exception {
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("products"));

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    void createOrder_withValidInput_shouldRedirectToHistory() throws Exception {
        when(productService.findById("prod-1")).thenReturn(product1);
        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);

        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor")
                .param("selectedProducts", "prod-1")
                .param("quantity-prod-1", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history?authorName=testAuthor"));

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void createOrder_withInvalidQuantity_shouldReturnErrorMessage() throws Exception {
        when(productService.findById("prod-1")).thenReturn(product1);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor")
                .param("selectedProducts", "prod-1")
                .param("quantity-prod-1", "0")) // Invalid quantity
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("products"));

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    void createOrder_withExceedingQuantity_shouldReturnErrorMessage() throws Exception {
        when(productService.findById("prod-1")).thenReturn(product1);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor")
                .param("selectedProducts", "prod-1")
                .param("quantity-prod-1", "15")) // Exceeds available quantity
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("products"));

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    void orderHistoryPage_withNoAuthorName_shouldReturnHistoryForm() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("historyForm"));
    }

    @Test
    void orderHistoryPage_withAuthorName_shouldReturnOrderHistory() throws Exception {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.findAllByAuthor("testAuthor")).thenReturn(orders);

        mockMvc.perform(get("/order/history")
                .param("authorName", "testAuthor"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistory"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("authorName"))
                .andExpect(model().attribute("orders", orders))
                .andExpect(model().attribute("authorName", "testAuthor"));
    }

    @Test
    void paymentOrderPage_shouldReturnPayOrderPage() throws Exception {
        when(orderService.findById("order-1")).thenReturn(testOrder);

        mockMvc.perform(get("/order/pay/{orderId}", "order-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payOrder"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("paymentMethods"))
                .andExpect(model().attribute("order", testOrder));
    }

    @Test
    void processPayment_withValidInput_shouldReturnConfirmationPage() throws Exception {
        when(orderService.findById("order-1")).thenReturn(testOrder);
        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(testPayment);

        mockMvc.perform(post("/order/pay/{orderId}", "order-1")
                .param("paymentMethod", "VOUCHER")
                .param("data", "value"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentConfirmation"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("payment", testPayment))
                .andExpect(model().attribute("order", testOrder));

        verify(paymentService, times(1)).addPayment(any(Order.class), eq("VOUCHER"), any(Map.class));
    }

    @Test
    void processPayment_withInvalidMethod_shouldReturnError() throws Exception {
        when(orderService.findById("order-1")).thenReturn(testOrder);

        mockMvc.perform(post("/order/pay/{orderId}", "order-1")
                .param("paymentMethod", "INVALID_METHOD"))
                .andExpect(status().isOk())
                .andExpect(view().name("payOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("paymentMethods"));

        verify(paymentService, never()).addPayment(any(Order.class), anyString(), any(Map.class));
    }

    @Test
    void createOrder_withNumberFormatException_shouldReturnErrorMessage() throws Exception {
        when(productService.findById("prod-1")).thenReturn(product1);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor")
                .param("selectedProducts", "prod-1")
                .param("quantity-prod-1", "not-a-number")) // Will cause NumberFormatException
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("products"));

        verify(orderService, never()).createOrder(any(Order.class));
    }

    @Test
    void orderHistoryPage_withEmptyAuthorName_shouldReturnHistoryForm() throws Exception {
        mockMvc.perform(get("/order/history")
                .param("authorName", "  ")) // Empty string with spaces
                .andExpect(status().isOk())
                .andExpect(view().name("historyForm"));
                
        verify(orderService, never()).findAllByAuthor(anyString());
    }

    @Test
    void processPayment_withCODMethod_shouldReturnConfirmationPage() throws Exception {
        when(orderService.findById("order-1")).thenReturn(testOrder);
        when(paymentService.addPayment(any(Order.class), anyString(), any(Map.class))).thenReturn(testPayment);

        mockMvc.perform(post("/order/pay/{orderId}", "order-1")
                .param("paymentMethod", "COD") // Testing the other valid payment method
                .param("data", "value"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentConfirmation"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attributeExists("order"));

        verify(paymentService, times(1)).addPayment(any(Order.class), eq("COD"), any(Map.class));
    }

@Test
void createOrder_withEmptyQuantityParam_shouldHandleErrors() throws Exception {
    when(productService.findById("prod-1")).thenReturn(product1);
    when(productService.findAll()).thenReturn(products);

    // Testing with an empty quantity parameter
    mockMvc.perform(post("/order/create")
            .param("authorName", "testAuthor")
            .param("selectedProducts", "prod-1")
            .param("quantity-prod-1", ""))  // Empty but present
            .andExpect(status().isOk())
            .andExpect(view().name("createOrder"))
            .andExpect(model().attributeExists("products"))
            .andExpect(model().attributeExists("errorMessage"));

    verify(orderService, never()).createOrder(any(Order.class));
}

    @Test
    void createOrder_withEmptySelectedProducts_shouldStillWork() throws Exception {
        when(productService.findAll()).thenReturn(products);
        
        // This tests when selectedProducts is provided but is an empty list
        mockMvc.perform(post("/order/create")
                .param("authorName", "testAuthor")
                .param("selectedProducts", ""))  // Empty selected product
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("products"));
                
        verify(orderService, never()).createOrder(any(Order.class));
    }
}