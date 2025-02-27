package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController extends AbstractItemController<Product> {
    
    private final ProductService productService;
    
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
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        return createPage(model, new Product());
    }

    @PostMapping("/create")
    public String createProductPost(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createProduct";  // Return to form on validation errors
        }
        
        productService.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        return listPage(model);
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        
        if (product == null) {
            // Use "../list" for the redirect instead of just "list"
            return "redirect:../list";
        }
        
        model.addAttribute("product", product);
        return "editProduct";
    }
    
    @PostMapping("/edit")
    public String editProductPost(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editProduct";  // Return to form on validation errors
        }
        
        productService.update(product);
        return "redirect:list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") String productId) {
        return delete(productId);
    }
}