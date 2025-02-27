package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter @Getter @NoArgsConstructor
public class Product {
    private String productId;
    
    @NotBlank(message = "Product name is required")
    private String productName;
    
    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Product quantity must be greater than or equal to 0")
    private Integer productQuantity;
}