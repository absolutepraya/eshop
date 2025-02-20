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
    
    // Repository for product data persistence
    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new product
     * @param product The product to create
     * @return The created product
     */
    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    /**
     * Retrieves all products
     * @return List of all products
     */
    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    /**
     * Updates an existing product
     * @param product The product with updated information
     * @return The updated product if found, null otherwise
     */
    @Override
    public Product edit(Product product) {
        List<Product> products = findAll();
        for (Product existingProduct : products) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                return productRepository.edit(product);
            }
        }
        return null;
    }

    /**
     * Deletes a product
     * @param id The ID of the product to delete
     */
    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }
}
