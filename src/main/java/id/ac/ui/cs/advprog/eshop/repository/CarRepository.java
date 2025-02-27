package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@Repository
public class CarRepository implements ItemRepository<Car> {
    private final List<Car> carData = new ArrayList<>();
    
    @Autowired
    private IdGeneratorService idGeneratorService;

    @Override
    public Car create(Car car){
        if(car.getCarId() == null){
            car.setCarId(idGeneratorService.generateId());
        }
        carData.add(car);
        return car;
    }

    @Override
    public Iterator<Car> findAll(){
        return carData.iterator();
    }

    @Override
    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public Car update(Car car) {
        for (int i = 0; i < carData.size(); i++) {
            Car existingCar = carData.get(i);
            if (existingCar.getCarId().equals(car.getCarId())) {
                // Update the existing car with the new information
                existingCar.setCarName(car.getCarName());
                existingCar.setCarColor(car.getCarColor());
                existingCar.setCarQuantity(car.getCarQuantity());
                return existingCar;
            }
        }
        return null; // Handle the case where the car is not found
    }

    @Override
    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
    
    /**
     * Legacy method for backward compatibility
     */
    public Car update(String id, Car updatedCar) {
        updatedCar.setCarId(id);
        return update(updatedCar);
    }
}