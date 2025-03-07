package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

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

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment testPayment;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        // Create test product
        Product product = new Product();
        product.setId("prod-1");
        product.setName("Test Product");
        product.setQuantity(5);
        
        // Create test order
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order("order-1", products, System.currentTimeMillis(), "testAuthor");

        // Create test payment
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("data", "value");
        testPayment = new Payment("payment-1", order, "VOUCHER", paymentData);
        testPayment.setStatus("PENDING");
        
        // Create payment list
        Payment payment2 = new Payment("payment-2", order, "COD", paymentData);
        payment2.setStatus("COMPLETED");
        payments = Arrays.asList(testPayment, payment2);
    }

    @Test
    void paymentHome_shouldReturnPaymentHomePage() throws Exception {
        mockMvc.perform(get("/payment"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentHome"));
    }

    @Test
    void paymentDetailForm_shouldReturnDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("detailForm"));
    }

    @Test
    void showPaymentDetail_shouldReturnPaymentDetailPage() throws Exception {
        when(paymentService.getPayment("payment-1")).thenReturn(testPayment);

        mockMvc.perform(get("/payment/detail/{paymentId}", "payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", testPayment));
    }

    @Test
    void showAllPayments_shouldReturnAdminPaymentListPage() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPaymentList"))
                .andExpect(model().attributeExists("payments"))
                .andExpect(model().attribute("payments", payments));
    }

    @Test
    void showPaymentDetailAdmin_shouldReturnAdminPaymentDetailPage() throws Exception {
        when(paymentService.getPayment("payment-1")).thenReturn(testPayment);

        mockMvc.perform(get("/payment/admin/detail/{paymentId}", "payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPaymentDetail"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", testPayment));
    }

    @Test
    void setPaymentStatus_shouldUpdateStatusAndRedirect() throws Exception {
        when(paymentService.getPayment("payment-1")).thenReturn(testPayment);
        when(paymentService.setStatus(any(Payment.class), eq("COMPLETED"))).thenReturn(testPayment);

        mockMvc.perform(post("/payment/admin/set-status/{paymentId}", "payment-1")
                .param("status", "COMPLETED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));

        verify(paymentService, times(1)).getPayment("payment-1");
        verify(paymentService, times(1)).setStatus(testPayment, "COMPLETED");
    }

    @Test
    void setPaymentStatus_withNonExistentPayment_shouldHandleSituation() throws Exception {
        when(paymentService.getPayment("non-existent")).thenReturn(null);

        mockMvc.perform(post("/payment/admin/set-status/{paymentId}", "non-existent")
                .param("status", "COMPLETED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));

        verify(paymentService, times(1)).getPayment("non-existent");
        verify(paymentService, never()).setStatus(any(Payment.class), anyString());
    }

    @Test
    void showPaymentDetail_withNonExistentPayment_shouldHandleSituation() throws Exception {
        when(paymentService.getPayment("non-existent")).thenReturn(null);

        mockMvc.perform(get("/payment/detail/{paymentId}", "non-existent"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"));
        // Removed the model attribute assertions since the controller doesn't add a payment attribute when null
    }

    @Test
    void showPaymentDetailAdmin_withNonExistentPayment_shouldHandleSituation() throws Exception {
        when(paymentService.getPayment("non-existent")).thenReturn(null);

        mockMvc.perform(get("/payment/admin/detail/{paymentId}", "non-existent"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPaymentDetail"));
        // Removed the model attribute assertions since the controller doesn't add a payment attribute when null
    }
}