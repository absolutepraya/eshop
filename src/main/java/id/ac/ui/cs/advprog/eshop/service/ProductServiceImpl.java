package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ItemRepository<Product> productRepositoryBean;
    
    public ProductServiceImpl(ItemRepository<Product> productRepositoryBean) {
        this.productRepositoryBean = productRepositoryBean;
    }

    @Override
    public Product create(Product product) {
        return productRepositoryBean.create(product);
    }

    @Override
    public java.util.List<Product> findAll() {
        java.util.Iterator<Product> productIterator = productRepositoryBean.findAll();
        java.util.List<Product> allProduct = new java.util.ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
    
    @Override
    public Product findById(String id) {
        return productRepositoryBean.findById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepositoryBean.update(product);
    }
    
    @Override
    public Product edit(Product product) {
        return update(product);
    }

    @Override
    public void delete(String id) {
        productRepositoryBean.delete(id);
    }
}