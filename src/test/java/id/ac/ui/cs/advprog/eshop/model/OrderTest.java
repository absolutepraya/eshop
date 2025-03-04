package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    private List<Product> products;
    
    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e59bcc84-ba86-4dbc-bb57-786c7f1d2d5d");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        
        Product product2 = new Product();
        product2.setProductId("ace0d66f-b506-4b74-bc49-d98d21bc5f3c");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        
        this.products.add(product1);
        this.products.add(product2);
    }
    
    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();
        
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                    this.products, 1708560000L, "Safira Sudrajat");
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat");
        
        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());
        assertEquals("568a4f50-0b93-4d1b-8826-7bebc93f4a37", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat", "SUCCESS");
        
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                    this.products, 1708560000L, "Safira Sudrajat", "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat");
        
        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat");
        
        assertThrows(IllegalArgumentException.class, () -> {
            order.setStatus("MEOW");
        });
    }
}
