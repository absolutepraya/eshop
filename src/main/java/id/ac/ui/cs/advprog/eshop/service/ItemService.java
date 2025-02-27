package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Identifiable;
import java.util.List;

/**
 * Generic service interface for all items
 * @param <T> The type of item this service manages
 */
public interface ItemService<T extends Identifiable> {
    T create(T item);
    List<T> findAll();
    T findById(String id);
    T update(T item);
    void delete(String id);
}