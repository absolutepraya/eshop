package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter 
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    // UUID to generate unique product ID
    public Product() {
        this.productId = UUID.randomUUID().toString();
    }
}