package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

public class ProductRepository extends AbstractItemRepository<Product> {
    
    public ProductRepository(IdGeneratorService idGeneratorService) {
        super(idGeneratorService);
    }
    
    /**
     * Updates an existing product in the repository
     * @param product The product with updated information
     * @return The updated product if found, null otherwise
     */
    @Override
    public Product update(Product product) {
        for (Product existingProduct : itemData) {
            if (existingProduct.getId().equals(product.getId())) {
                existingProduct.setName(product.getName());
                existingProduct.setQuantity(product.getQuantity());
                
                // For backward compatibility
                existingProduct.setProductName(product.getProductName());
                existingProduct.setProductQuantity(product.getProductQuantity());
                
                return existingProduct;
            }
        }
        return null;
    }
}