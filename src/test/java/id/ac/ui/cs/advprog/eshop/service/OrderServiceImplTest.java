package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("f583dcd4-1407-4acf-b109-dbf1191f4b19");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("e156660d-8011-4765-bcc2-bea5a5e5de2a",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("dafd5939-7a89-444e-9618-16a6cdfb6ca3",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testCreateOrder(){
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);

        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testCreateOrderIfAlreadyExists(){
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertNull(orderService.createOrder(order));
        verify(orderRepository, times(0)).save(order);
    }

    @Test
    void testUpdateStatus(){
        Order order = orders.get(1);
        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
                order.getAuthor(), OrderStatus.SUCCESS.getValue());
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(newOrder).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(order.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidStatus(){
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "MEOW"));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidOrderId(){
        doReturn(null).when(orderRepository).findById("zczc");

        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("zczc", OrderStatus.SUCCESS.getValue()));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testFindByIdIfIdFound(){
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Order result  = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound(){
        doReturn(null).when(orderRepository).findById("zczc");
        assertNull(orderService.findById("zczc"));
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect(){
        Order order = orders.get(1);
        doReturn(orders).when(orderRepository).findAllByAuthor(order.getAuthor());

        List<Order> results = orderService.findAllByAuthor(order.getAuthor());
        for (Order result: results){
            assertEquals(order.getAuthor(), result.getAuthor());
        }
        assertEquals(2, results.size());
    }

    @Test
    void testFindAllByAuthorIfAllLowercase() {
        Order order = orders.get(1);
        doReturn(new ArrayList<Order>()).when(orderRepository)
                .findAllByAuthor(order.getAuthor().toLowerCase());
        List<Order> results = orderService.findAllByAuthor(
                order.getAuthor().toLowerCase());
        assertTrue(results.isEmpty());
    }
}