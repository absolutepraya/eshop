package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class Car {
    private String carId;
    private String carName; 
    private String carColor;
    private int carQuantity;
}