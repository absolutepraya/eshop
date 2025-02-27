package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;

@Service
public class CarServiceImpl extends AbstractItemService<Car, ItemRepository<Car>> implements CarService {
    
    public CarServiceImpl(ItemRepository<Car> carRepositoryBean) {
        this.repository = carRepositoryBean;
    }
    
    @Override
    public void update(String carId, Car car) {
        car.setId(carId);
        repository.update(car);
    }
    
    @Override
    public void deleteCarById(String carId) {
        delete(carId);
    }
}