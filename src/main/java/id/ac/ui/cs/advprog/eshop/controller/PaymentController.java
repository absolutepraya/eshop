package id.ac.ui.cs.advprog.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("")
    public String paymentHome(Model model) {
        return "paymentHome";
    }

    @GetMapping("/detail")
    public String paymentDetailForm(Model model) {
        return "detailForm";
    }

    @GetMapping("/detail/{paymentId}")
    public String showPaymentDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "paymentDetail";
    }

    @GetMapping("/admin/list")
    public String showAllPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "adminPaymentList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String showPaymentDetailAdmin(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "adminPaymentDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String setPaymentStatus(@PathVariable String paymentId, 
                                @RequestParam("status") String status,
                                Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        payment = paymentService.setStatus(payment, status);
        model.addAttribute("payment", payment);
        return "redirect:/payment/admin/list";
    }
}