package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarService extends ItemService<Car> {
    // Legacy method for backward compatibility
    void update(String carId, Car car);
    
    // Legacy method for backward compatibility
    void deleteCarById(String carId);
}