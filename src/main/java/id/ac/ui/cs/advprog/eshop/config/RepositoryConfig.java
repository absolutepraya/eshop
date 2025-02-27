package id.ac.ui.cs.advprog.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@Configuration
public class RepositoryConfig {
    
    @Bean
    public ItemRepository<Product> productRepositoryBean(IdGeneratorService idGeneratorService) {
        return new ProductRepository(idGeneratorService);
    }
    
    @Bean
    public ItemRepository<Car> carRepositoryBean(IdGeneratorService idGeneratorService) {
        return new CarRepository(idGeneratorService);
    }
}