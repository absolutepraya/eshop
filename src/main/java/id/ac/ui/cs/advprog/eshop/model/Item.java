package id.ac.ui.cs.advprog.eshop.model;

/**
 * Base interface for all items in the shop
 */
public interface Item {
    String getId();
    void setId(String id);
    String getName();
    void setName(String name);
    int getQuantity();
    void setQuantity(int quantity);
}