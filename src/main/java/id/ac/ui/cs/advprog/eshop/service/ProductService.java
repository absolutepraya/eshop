package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductService extends ItemService<Product> {
    // No need for legacy methods as they violate LSP
}