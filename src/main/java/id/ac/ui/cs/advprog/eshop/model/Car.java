package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class Car implements Item {
    private String carId;
    private String carName; 
    private String carColor;
    private int carQuantity;

    @Override
    public String getId() {
        return this.carId;
    }

    @Override
    public void setId(String id) {
        this.carId = id;
    }

    @Override
    public String getName() {
        return this.carName;
    }

    @Override
    public void setName(String name) {
        this.carName = name;
    }

    @Override
    public int getQuantity() {
        return this.carQuantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.carQuantity = quantity;
    }
    
    // Car-specific method
    public String getColor() {
        return this.carColor;
    }
    
    // Car-specific method
    public void setColor(String color) {
        this.carColor = color;
    }
}