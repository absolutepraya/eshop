package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController extends AbstractItemController<Product> {
    
    @Autowired
    public ProductController(ProductService productService) {
        super(
            productService,
            "redirect:list",
            "createProduct",
            "productList",
            "editProduct",
            "product",
            "products"
        );
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        return createPage(model, new Product());
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        return createPost(product);
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        return listPage(model);
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        return editPage(id, model);
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        return editPost(product);
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") String productId) {
        return delete(productId);
    }
}