package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Item;
import java.util.Iterator;

/**
 * Generic repository interface for all items
 * @param <T> The type of item this repository manages
 */
public interface ItemRepository<T extends Item> {
    T create(T item);
    Iterator<T> findAll();
    T findById(String id);
    T update(T item);
    void delete(String id);
}