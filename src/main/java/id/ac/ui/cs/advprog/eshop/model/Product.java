package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter @Getter @NoArgsConstructor
public class Product implements Item {
    private String productId;
    
    @NotBlank(message = "Product name is required")
    private String productName;
    
    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Product quantity must be greater than or equal to 0")
    private Integer productQuantity;

    @Override
    public String getId() {
        return this.productId;
    }

    @Override
    public void setId(String id) {
        this.productId = id;
    }

    @Override
    public String getName() {
        return this.productName;
    }

    @Override
    public void setName(String name) {
        this.productName = name;
    }

    @Override
    public int getQuantity() {
        return this.productQuantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.productQuantity = quantity;
    }
}