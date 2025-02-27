package id.ac.ui.cs.advprog.eshop.repository;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@Repository
public class CarRepository extends AbstractItemRepository<Car> {
    
    public CarRepository(IdGeneratorService idGeneratorService) {
        super(idGeneratorService);
    }
    
    @Override
    public Car update(Car car) {
        for (Car existingCar : itemData) {
            if (existingCar.getId().equals(car.getId())) {
                // Update the existing car with the new information
                existingCar.setName(car.getName());
                existingCar.setQuantity(car.getQuantity());
                existingCar.setColor(car.getColor());
                
                // For backward compatibility
                existingCar.setCarName(car.getCarName());
                existingCar.setCarQuantity(car.getCarQuantity());
                existingCar.setCarColor(car.getCarColor());
                
                return existingCar;
            }
        }
        return null; // Handle the case where the car is not found
    }
    
    /**
     * Legacy method for backward compatibility
     */
    public Car update(String id, Car updatedCar) {
        updatedCar.setId(id);
        return update(updatedCar);
    }
}