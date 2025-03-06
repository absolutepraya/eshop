package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        // TODO: Implement constructor
    }

    public void setStatus(String status) {
        // TODO: Implement status setter
    }
}