package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractItemTest {
    
    // Concrete implementation of AbstractItem for testing
    private static class ConcreteItem extends AbstractItem {
        // No additional implementation needed
    }
    
    private ConcreteItem item;
    
    @BeforeEach
    void setUp() {
        item = new ConcreteItem();
    }
    
    @Test
    void testGetAndSetId() {
        String id = "test-id-123";
        item.setId(id);
        assertEquals(id, item.getId());
    }
    
    @Test
    void testGetAndSetName() {
        String name = "Test Item";
        item.setName(name);
        assertEquals(name, item.getName());
    }
    
    @Test
    void testGetAndSetQuantity() {
        int quantity = 10;
        item.setQuantity(quantity);
        assertEquals(quantity, item.getQuantity());
    }
    
    @Test
    void testInitialState() {
        // Test default constructor sets values properly
        assertNull(item.getId());
        assertNull(item.getName());
        assertEquals(0, item.getQuantity());
    }
}