package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;

@Service
public class ProductServiceImpl extends AbstractItemService<Product, ItemRepository<Product>> implements ProductService {
    
    public ProductServiceImpl(ItemRepository<Product> productRepositoryBean) {
        this.repository = productRepositoryBean;
    }
}