package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product edit(Product product) {
        List<Product> products = findAll();
        for (Product existingProduct : products) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                existingProduct.setProductName(product.getProductName());
                existingProduct.setProductQuantity(product.getProductQuantity());
                return existingProduct;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }
}
