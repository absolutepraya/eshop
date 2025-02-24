package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarService {
    public Car create(Car car);
    public List<Car> findAll();
    Car findById(String carId);
    public void update(String carId, Car car);
    public void deleteCarById(String carId);
}
