package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@Service
public class ProductServiceImpl extends AbstractItemService<Product, ProductRepository> implements ProductService {
    /**
     * Legacy method for backward compatibility
     */
    @Override
    public Product edit(Product product) {
        return update(product);
    }
}