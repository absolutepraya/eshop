package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@Repository
public class ProductRepository {
    // In-memory storage for products
    private final List<Product> productData = new ArrayList<>();
    
    @Autowired
    private IdGeneratorService idGeneratorService;

    /**
     * Creates a new product in the repository
     * @param product The product to be created
     * @return The created product
     */
    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(idGeneratorService.generateId());
        }
        productData.add(product);
        return product;
    }

    /**
     * Retrieves all products from the repository
     * @return Iterator of all products
     */
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    /**
     * Updates an existing product in the repository
     * @param product The product with updated information
     * @return The updated product if found, null otherwise
     */
    public Product edit(Product product) {
        for (Product existingProduct : productData) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                existingProduct.setProductName(product.getProductName());
                existingProduct.setProductQuantity(product.getProductQuantity());
                return existingProduct;
            }
        }
        return null;
    }

    /**
     * Deletes a product from the repository
     * @param id The ID of the product to delete
     */
    public void delete(String id) {
        productData.removeIf(product -> product.getProductId().equals(id));
    }
}