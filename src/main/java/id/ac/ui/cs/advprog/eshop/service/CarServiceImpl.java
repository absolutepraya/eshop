package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

@Service
public class CarServiceImpl extends AbstractItemService<Car, CarRepository> implements CarService {
    /**
     * Legacy method for backward compatibility
     */
    @Override
    public void update(String carId, Car car) {
        car.setId(carId);
        repository.update(car);
    }
    
    /**
     * Legacy method for backward compatibility
     */
    @Override
    public void deleteCarById(String carId) {
        delete(carId);
    }
}