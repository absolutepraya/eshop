package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

@Controller
@RequestMapping("/car")
public class CarController extends AbstractItemController<Car> {
    
    private final CarService carService;
    
    @Autowired
    public CarController(CarService carService) {
        super(
            carService,
            "redirect:list",
            "createCar",
            "carList",
            "editCar",
            "car",
            "cars"
        );
        this.carService = carService;
    }

    @GetMapping("/create")
    public String createCarPage(Model model) {
        return createPage(model, new Car());
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        return createPost(car);
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        return listPage(model);
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable String id, Model model) {
        return editPage(id, model);
    }

    @PostMapping("/edit")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        return editPost(car);
    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam("id") String carId) {
        return delete(carId);
    }
    
    // Legacy endpoints for backward compatibility
    
    @GetMapping("/createCar")
    public String legacyCreateCarPage(Model model) {
        return createCarPage(model);
    }
    
    @PostMapping("/createCar")
    public String legacyCreateCarPost(@ModelAttribute Car car, Model model) {
        return createCarPost(car, model);
    }
    
    @GetMapping("/listCar")
    public String legacyCarListPage(Model model) {
        return carListPage(model);
    }
    
    @GetMapping("/editCar/{carId}")
    public String legacyEditCarPage(@PathVariable String carId, Model model) {
        return editCarPage(carId, model);
    }
    
    @PostMapping("/editCar")
    public String legacyEditCarPost(@ModelAttribute Car car, Model model) {
        return editCarPost(car, model);
    }
    
    @PostMapping("/deleteCar")
    public String legacyDeleteCar(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:listCar";
    }
}