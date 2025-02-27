package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductService extends ItemService<Product> {
    // Legacy method for backward compatibility
    Product edit(Product product);
}