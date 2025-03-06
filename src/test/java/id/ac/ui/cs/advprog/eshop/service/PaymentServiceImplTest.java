package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    private Order testOrder;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        // Create a sample order
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);
        
        testOrder = new Order("a2f54967-45f9-4b3d-8b69-89bf94319cb9", products, 1710000000L, "Test User");
        
        // Create sample payments
        payments = new ArrayList<>();
        
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP12345678ABC");
        Payment voucherPayment = new Payment("payment1", testOrder, "VOUCHER", voucherData);
        voucherPayment.setStatus("SUCCESS");
        payments.add(voucherPayment);
        
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Test Address");
        codData.put("deliveryFee", "10000");
        Payment codPayment = new Payment("payment2", testOrder, "COD", codData);
        codPayment.setStatus("SUCCESS");
        payments.add(codPayment);
    }

    @Test
    void testAddPaymentWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", paymentData);
        
        assertEquals("VOUCHER", result.getMethod());
        assertEquals("SUCCESS", result.getStatus());
        // The implementation calls save twice: once in the processVoucherPayment via setStatus, and once in addPayment
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("SUCCESS"));
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", paymentData);
        
        assertEquals("VOUCHER", result.getMethod());
        assertEquals("REJECTED", result.getStatus());
        // The implementation calls save twice: once in the processVoucherPayment via setStatus, and once in addPayment
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("FAILED"));
    }

    @Test
    void testAddPaymentWithValidCOD() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Test Address");
        paymentData.put("deliveryFee", "10000");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Payment result = paymentService.addPayment(testOrder, "COD", paymentData);
        
        assertEquals("COD", result.getMethod());
        assertEquals("SUCCESS", result.getStatus());
        // The implementation calls save twice: once in the processCodPayment via setStatus, and once in addPayment
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("SUCCESS"));
    }

    @Test
    void testAddPaymentWithInvalidCOD() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", ""); // Empty address
        paymentData.put("deliveryFee", "10000");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Payment result = paymentService.addPayment(testOrder, "COD", paymentData);
        
        assertEquals("COD", result.getMethod());
        assertEquals("REJECTED", result.getStatus());
        // The implementation calls save twice: once in the processCodPayment via setStatus, and once in addPayment
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("FAILED"));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = payments.get(0);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        
        Payment result = paymentService.setStatus(payment, "SUCCESS");
        
        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus(eq(payment.getOrder().getId()), eq("SUCCESS"));
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = payments.get(0);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        
        Payment result = paymentService.setStatus(payment, "REJECTED");
        
        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus(eq(payment.getOrder().getId()), eq("FAILED"));
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        
        Payment result = paymentService.getPayment(payment.getId());
        
        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(payments);
        
        List<Payment> result = paymentService.getAllPayments();
        
        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }
    
    @Test
    void testIsValidVoucherCode() {
        // Refactor the test to focus only on valid voucher cases
        Map<String, String> validData = new HashMap<>();
        validData.put("voucherCode", "ESHOP12345678ABC");
        
        // Mock the behavior to return the modified payment
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = (Payment)invocation.getArgument(0);
            return p;
        });
        
        // Test with valid voucher code - should be SUCCESS
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", validData);
        assertEquals("SUCCESS", result.getStatus());
        
        // Verify that save is called twice for this valid voucher case
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("SUCCESS"));
    }
    
    @Test
    void testInvalidVoucherCodePrefix() {
        // Test invalid voucher code - wrong prefix
        Map<String, String> invalidPrefixData = new HashMap<>();
        invalidPrefixData.put("voucherCode", "WRONG12345678ABC");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = (Payment)invocation.getArgument(0);
            return p;
        });
        
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", invalidPrefixData);
        assertEquals("REJECTED", result.getStatus());
        
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("FAILED"));
    }
    
    @Test
    void testInvalidVoucherCodeLength() {
        // Test invalid voucher code - wrong length
        Map<String, String> invalidLengthData = new HashMap<>();
        invalidLengthData.put("voucherCode", "ESHOP123");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = (Payment)invocation.getArgument(0);
            return p;
        });
        
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", invalidLengthData);
        assertEquals("REJECTED", result.getStatus());
        
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("FAILED"));
    }
    
    @Test
    void testInvalidVoucherCodeNumbers() {
        // Test invalid voucher code - not enough numbers
        Map<String, String> invalidNumbersData = new HashMap<>();
        invalidNumbersData.put("voucherCode", "ESHOPABCDEFGHIJK");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = (Payment)invocation.getArgument(0);
            return p;
        });
        
        Payment result = paymentService.addPayment(testOrder, "VOUCHER", invalidNumbersData);
        assertEquals("REJECTED", result.getStatus());
        
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(eq(testOrder.getId()), eq("FAILED"));
    }
}