package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Identifiable;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract repository implementation for all items
 * @param <T> The type of item this repository manages
 */
public abstract class AbstractItemRepository<T extends Identifiable> implements ItemRepository<T> {
    protected final List<T> itemData = new ArrayList<>();
    
    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Override
    public T create(T item) {
        if (item.getId() == null) {
            item.setId(idGeneratorService.generateId());
        }
        itemData.add(item);
        return item;
    }

    @Override
    public Iterator<T> findAll() {
        return itemData.iterator();
    }

    @Override
    public T findById(String id) {
        for (T item : itemData) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        itemData.removeIf(item -> item.getId().equals(id));
    }
    
    /**
     * This method should be implemented by subclasses to handle
     * type-specific update logic
     */
    @Override
    public abstract T update(T item);
}