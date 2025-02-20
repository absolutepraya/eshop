package id.ac.ui.cs.advprog.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    // Inject the product service
    @Autowired
    private ProductService service;

    /**
     * Handles GET request to show the create product form
     * @param model Model object to pass data to view
     * @return View name for create product page
     */
    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    /**
     * Handles POST request to create a new product
     * @param product Product object from form submission
     * @param model Model object to pass data to view
     * @return Redirects to product list page
     */
    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product, 
                                  BindingResult bindingResult, 
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "createProduct";
        }
        service.create(product);
        return "redirect:list";
    }

    /**
     * Handles GET request to show list of all products
     * @param model Model object to pass data to view
     * @return View name for product list page
     */
    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    /**
     * Handles GET request to show the edit product form
     * @param id Product ID to edit
     * @param model Model object to pass data to view
     * @return View name for edit product page or redirects to list if product not found
     */
    @GetMapping("/edit")
    public String editProductPage(@RequestParam String id, Model model) {
        List<Product> products = service.findAll();
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                model.addAttribute("product", product);
                return "editProduct";
            }
        }
        return "redirect:list";
    }

    /**
     * Handles POST request to update an existing product
     * @param product Updated product object from form submission
     * @param model Model object to pass data to view
     * @return Redirects to product list page
     */
    @PostMapping("/edit")
    public String editProductPost(@Valid @ModelAttribute Product product, 
                                BindingResult bindingResult, 
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "editProduct";
        }
        service.edit(product);
        return "redirect:list";
    }

    /**
     * Handles GET request to delete a product
     * @param id Product ID to delete
     * @return Redirects to product list page
     */
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam String id) {
        service.delete(id);
        return "redirect:list";
    }
}
