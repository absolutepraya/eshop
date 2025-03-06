package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    private Order order;
    private Map<String, String> paymentData;
    
    @BeforeEach
    void setUp() {
        // Create a sample order
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);
        
        this.order = new Order("a2f54967-45f9-4b3d-8b69-89bf94319cb9", products, 1710000000L, "Test User");
        
        // Create sample payment data
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP12345678ABC");
    }
    
    @Test
    void testCreatePayment() {
        Payment payment = new Payment("test-payment-id", order, "VOUCHER", paymentData);
        
        assertEquals("test-payment-id", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("PENDING", payment.getStatus()); // Default status
    }
    
    @Test
    void testSetStatus() {
        Payment payment = new Payment("test-payment-id", order, "VOUCHER", paymentData);
        payment.setStatus("SUCCESS");
        
        assertEquals("SUCCESS", payment.getStatus());
    }
}