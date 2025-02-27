package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.ui.Model;

import id.ac.ui.cs.advprog.eshop.model.Item;
import id.ac.ui.cs.advprog.eshop.service.ItemService;

/**
 * Abstract base controller that provides common functionality for item controllers
 * @param <T> The type of item this controller manages
 */
public abstract class AbstractItemController<T extends Item> {
    
    protected final ItemService<T> itemService;
    protected final String redirectList;
    protected final String createView;
    protected final String listView;
    protected final String editView;
    protected final String itemAttribute;
    protected final String itemsAttribute;
    
    public AbstractItemController(
            ItemService<T> itemService, 
            String redirectList,
            String createView,
            String listView,
            String editView,
            String itemAttribute,
            String itemsAttribute) {
        this.itemService = itemService;
        this.redirectList = redirectList;
        this.createView = createView;
        this.listView = listView;
        this.editView = editView;
        this.itemAttribute = itemAttribute;
        this.itemsAttribute = itemsAttribute;
    }
    
    protected String createPage(Model model, T item) {
        model.addAttribute(itemAttribute, item);
        return createView;
    }
    
    protected String createPost(T item) {
        itemService.create(item);
        return redirectList;
    }
    
    protected String listPage(Model model) {
        model.addAttribute(itemsAttribute, itemService.findAll());
        return listView;
    }
    
    protected String editPage(String id, Model model) {
        model.addAttribute(itemAttribute, itemService.findById(id));
        return editView;
    }
    
    protected String editPost(T item) {
        itemService.update(item);
        return redirectList;
    }
    
    protected String delete(String id) {
        itemService.delete(id);
        return redirectList;
    }
}