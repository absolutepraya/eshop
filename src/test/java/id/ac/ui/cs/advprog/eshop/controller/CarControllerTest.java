package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void createCarPage_shouldReturnCreatePage() throws Exception {
        mockMvc.perform(get("/car/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void createCarPost_shouldCreateCarAndRedirect() throws Exception {
        Car car = new Car();
        car.setCarId("test-id");
        when(carService.create(any(Car.class))).thenReturn(car);
        
        mockMvc.perform(post("/car/create")
                .param("carId", "test-id")
                .param("carName", "Test Car")
                .param("carColor", "Red")
                .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
        
        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void carListPage_shouldReturnListPage() throws Exception {
        Car car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Car 1");
        
        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Car 2");
        
        List<Car> carList = Arrays.asList(car1, car2);
        
        when(carService.findAll()).thenReturn(carList);
        
        mockMvc.perform(get("/car/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attribute("cars", carList));
    }

    @Test
    void editCarPage_shouldReturnEditPage() throws Exception {
        Car car = new Car();
        car.setCarId("test-id");
        car.setCarName("Test Car");
        
        when(carService.findById("test-id")).thenReturn(car);

        mockMvc.perform(get("/car/edit/{id}", "test-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void editCarPost_shouldUpdateCarAndRedirect() throws Exception {
        Car car = new Car();
        car.setCarId("test-id");
        when(carService.update(any(Car.class))).thenReturn(car);
        
        mockMvc.perform(post("/car/edit")
                .param("carId", "test-id")
                .param("carName", "Updated Car")
                .param("carColor", "Blue")
                .param("carQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
        
        verify(carService, times(1)).update(any(Car.class));
    }

    @Test
    void deleteCar_shouldDeleteCarAndRedirect() throws Exception {
        String carId = "test-id";
        
        mockMvc.perform(post("/car/delete")
                .param("id", carId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
        
        verify(carService, times(1)).delete(carId);
    }
}