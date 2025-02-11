# E-Shop (Adv. Programming Project)

## Reflection 1

Understanding how list-product (basically GET all products) works is relatively easy. The code from the tutorial is straightforward and easy to understand.

Next, we are asked to implement edit and delete product feature on our own. Implementing both features were not that difficult. The code from the tutorial was a good reference to start with. 

**Note**:
Before starting, I added UUID to the product class so each product has a unique identifier.

1. For each method, I started with editing the `ProductService.java` and `ProductServiceImpl.java`, I added the new method `editProduct` which takes a product object as parameter. 
2. Then, I add both methods to `ProductRepository.java` so that the methods can be used in the controller. 
3. Next, I added the edit product mapping to `ProductController.java`, which makes 2 calls to the service layer, one to get the page and another to save the updated product.
4. Finally, I created a new HTML file `EditProduct.html` and added the form to edit the product.
5. I basically repeated the same steps for the delete product feature.

Afterall, for me Spring Boot is like a combination of a Django and Express.js. It is understandable and straightforward.