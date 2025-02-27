package id.ac.ui.cs.advprog.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;

@Configuration
public class ServiceConfig {
    
    @Bean
    public ProductService productService(ItemRepository<Product> productRepositoryBean) {
        return new ProductServiceImpl(productRepositoryBean);
    }
    
    @Bean
    public CarService carService(ItemRepository<Car> carRepositoryBean) {
        return new CarServiceImpl(carRepositoryBean);
    }
}