package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, order, method, paymentData);
        
        // Process payment based on method
        if ("VOUCHER".equals(method)) {
            processVoucherPayment(payment);
        } else if ("COD".equals(method)) {
            processCodPayment(payment);
        }
        
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        
        // Update order status based on payment status
        if ("SUCCESS".equals(status)) {
            orderService.updateStatus(payment.getOrder().getId(), "SUCCESS");
        } else if ("REJECTED".equals(status)) {
            orderService.updateStatus(payment.getOrder().getId(), "FAILED");
        }
        
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    // Implementation for "Payment by Voucher Code" sub-feature
    private void processVoucherPayment(Payment payment) {
        Map<String, String> paymentData = payment.getPaymentData();
        String voucherCode = paymentData.get("voucherCode");
        
        if (isValidVoucherCode(voucherCode)) {
            setStatus(payment, "SUCCESS");
        } else {
            setStatus(payment, "REJECTED");
        }
    }
    
    // Implementation for "Cash on Delivery" sub-feature
    private void processCodPayment(Payment payment) {
        Map<String, String> paymentData = payment.getPaymentData();
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        
        if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
            setStatus(payment, "REJECTED");
        }
        // For valid COD payments, leave status as PENDING
        // The status will be set to SUCCESS or REJECTED by admin later
    }
    
    // Validate voucher code according to requirements
    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }
        
        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }
        
        // Count numerical characters in the voucher code
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(voucherCode);
        
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        
        return count == 8;
    }
}