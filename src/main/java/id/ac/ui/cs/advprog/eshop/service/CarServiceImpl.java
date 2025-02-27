package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;

@Service
public class CarServiceImpl implements CarService {

    private final ItemRepository<Car> carRepositoryBean;
    
    public CarServiceImpl(ItemRepository<Car> carRepositoryBean) {
        this.carRepositoryBean = carRepositoryBean;
    }

    @Override
    public Car create(Car car) {
        return carRepositoryBean.create(car);
    }

    @Override
    public java.util.List<Car> findAll() {
        java.util.Iterator<Car> carIterator = carRepositoryBean.findAll();
        java.util.List<Car> allCar = new java.util.ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }

    @Override
    public Car findById(String carId) {
        return carRepositoryBean.findById(carId);
    }

    @Override
    public Car update(Car car) {
        return carRepositoryBean.update(car);
    }
    
    @Override
    public void update(String carId, Car car) {
        car.setId(carId);
        carRepositoryBean.update(car);
    }

    @Override
    public void delete(String carId) {
        carRepositoryBean.delete(carId);
    }
    
    @Override
    public void deleteCarById(String carId) {
        delete(carId);
    }
}