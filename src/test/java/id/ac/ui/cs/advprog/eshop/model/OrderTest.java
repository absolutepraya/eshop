package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

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
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat", OrderStatus.SUCCESS.getValue());
        
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
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
        
        order.setStatus(OrderStatus.CANCELLED.getValue());
        assertEquals(OrderStatus.CANCELLED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("568a4f50-0b93-4d1b-8826-7bebc93f4a37",
                this.products, 1708560000L, "Safira Sudrajat");
        
        assertThrows(IllegalArgumentException.class, () -> {
            order.setStatus("MEOW");
        });
    }

    @Test
    void testOrderBuilderWithAllFields() {
        Order order = Order.builder()
                .id("568a4f50-0b93-4d1b-8826-7bebc93f4a37")
                .products(this.products)
                .orderTime(1708560000L)
                .author("Safira Sudrajat")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();
        
        assertNotNull(order);
        assertEquals("568a4f50-0b93-4d1b-8826-7bebc93f4a37", order.getId());
        assertSame(this.products, order.getProducts());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }
    
    @Test
    void testOrderBuilderWithDifferentStatus() {
        Order order = Order.builder()
                .id("568a4f50-0b93-4d1b-8826-7bebc93f4a37")
                .products(this.products)
                .orderTime(1708560000L)
                .author("Safira Sudrajat")
                .status(OrderStatus.SUCCESS.getValue())
                .build();
        
        assertNotNull(order);
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }
    
    @Test
    void testOrderBuilderWithEmptyId() {
        Order order = Order.builder()
                .products(this.products)
                .orderTime(1708560000L)
                .author("Safira Sudrajat")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();
        
        assertNotNull(order);
        assertNull(order.getId());
        assertSame(this.products, order.getProducts());
        assertEquals("Safira Sudrajat", order.getAuthor());
    }
    
    @Test
    void testOrderBuilderToString() {
        Order.OrderBuilder builder = Order.builder()
                .id("568a4f50-0b93-4d1b-8826-7bebc93f4a37")
                .products(this.products)
                .orderTime(1708560000L)
                .author("Safira Sudrajat")
                .status(OrderStatus.WAITING_PAYMENT.getValue());
                
        String builderString = builder.toString();
        assertNotNull(builderString);
        assertTrue(builderString.contains("568a4f50-0b93-4d1b-8826-7bebc93f4a37"));
        assertTrue(builderString.contains("Safira Sudrajat"));
    }
}
