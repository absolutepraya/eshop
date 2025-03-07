package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @GetMapping({"", "/"})
    public String orderHomePage(Model model) {
        return "orderHome";
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam("authorName") String authorName,
                            @RequestParam(value = "selectedProducts", required = false) List<String> selectedProductIds,
                            @RequestParam Map<String, String> allParams,
                            Model model) {
        
        // Validate selection
        if (selectedProductIds == null || selectedProductIds.isEmpty()) {
            model.addAttribute("errorMessage", "Please select at least one product");
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "createOrder";
        }
        
        try {
            // Get selected products with quantities
            List<Product> selectedProducts = new ArrayList<>();
            
            for (String productId : selectedProductIds) {
                Product product = productService.findById(productId);
                
                // Get quantity parameter for this product
                String quantityParam = "quantity-" + productId;
                if (allParams.containsKey(quantityParam)) {
                    int requestedQuantity = Integer.parseInt(allParams.get(quantityParam));
                    
                    // Validate quantity
                    if (requestedQuantity <= 0 || requestedQuantity > product.getQuantity()) {
                        model.addAttribute("errorMessage", "Invalid quantity for product: " + product.getName());
                        List<Product> products = productService.findAll();
                        model.addAttribute("products", products);
                        return "createOrder";
                    }
                    
                    // Create a new product instance with the requested quantity
                    Product orderProduct = new Product();
                    orderProduct.setId(product.getId());
                    orderProduct.setName(product.getName());
                    orderProduct.setQuantity(requestedQuantity);
                    
                    selectedProducts.add(orderProduct);
                }
            }
            
            // Create order
            String orderId = UUID.randomUUID().toString();
            long orderTime = System.currentTimeMillis();
            
            Order newOrder = new Order(orderId, selectedProducts, orderTime, authorName);
            Order createdOrder = orderService.createOrder(newOrder);
            
            // Redirect to order history page or show confirmation
            return "redirect:/order/history?authorName=" + authorName;
            
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Error creating order: " + e.getMessage());
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "createOrder";
        }
    }

    @GetMapping("/history")
    public String orderHistoryPage(@RequestParam(value = "authorName", required = false) String authorName, Model model) {
        // If authorName is provided, show the order history for that author
        if (authorName != null && !authorName.trim().isEmpty()) {
            List<Order> orders = orderService.findAllByAuthor(authorName);
            model.addAttribute("orders", orders);
            model.addAttribute("authorName", authorName);
            return "orderHistory";
        }
        
        // If no authorName is provided, show the history form
        return "historyForm";
    }

    @GetMapping("/pay/{orderId}")
    public String paymentOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        // Update payment methods to match the requirements
        model.addAttribute("paymentMethods", new String[]{"VOUCHER", "COD"});
        return "payOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String processPayment(@PathVariable String orderId, 
                            @RequestParam("paymentMethod") String paymentMethod,
                            @RequestParam Map<String, String> allParams,
                            Model model) {
        Order order = orderService.findById(orderId);
        
        // Remove non-payment data fields from parameters
        Map<String, String> paymentData = new HashMap<>(allParams);
        paymentData.remove("_csrf");
        paymentData.remove("paymentMethod");
        
        // Ensure paymentMethod is either "VOUCHER" or "COD"
        if (!paymentMethod.equals("VOUCHER") && !paymentMethod.equals("COD")) {
            model.addAttribute("errorMessage", "Invalid payment method. Please choose either VOUCHER or COD.");
            model.addAttribute("order", order);
            model.addAttribute("paymentMethods", new String[]{"VOUCHER", "COD"});
            return "payOrder";
        }
        
        // Create payment
        Payment payment = paymentService.addPayment(order, paymentMethod, paymentData);
        
        model.addAttribute("payment", payment);
        model.addAttribute("order", order);
        return "paymentConfirmation";
    }
}