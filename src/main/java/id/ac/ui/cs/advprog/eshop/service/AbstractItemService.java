package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Item;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract service implementation for all items
 * @param <T> The type of item this service manages
 * @param <R> The type of repository this service uses
 */
public abstract class AbstractItemService<T extends Item, R extends ItemRepository<T>> implements ItemService<T> {
    
    @Autowired
    protected R repository;

    @Override
    public T create(T item) {
        return repository.create(item);
    }

    @Override
    public List<T> findAll() {
        Iterator<T> itemIterator = repository.findAll();
        List<T> allItems = new ArrayList<>();
        itemIterator.forEachRemaining(allItems::add);
        return allItems;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id);
    }

    @Override
    public T update(T item) {
        return repository.update(item);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}