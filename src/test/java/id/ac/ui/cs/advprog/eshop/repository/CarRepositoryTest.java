package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {
    
    @Mock
    private IdGeneratorService idGeneratorService;
    
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository(idGeneratorService);
        // Make the stubbing lenient so it doesn't fail tests that don't use it
        lenient().when(idGeneratorService.generateId()).thenReturn("generated-id");
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarName("Toyota Camry");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
        assertEquals("generated-id", savedCar.getCarId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test 
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car1.setCarName("Toyota Camry");
        car1.setCarColor("Black");
        car1.setCarQuantity(10);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("a0f9de46-90b1-437d-a0bf-d0821d6e9096");
        car2.setCarName("Honda Accord");
        car2.setCarColor("White");
        car2.setCarQuantity(5);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }
    
    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarId("test-id");
        car.setCarName("Toyota Camry");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        carRepository.create(car);
        
        Car foundCar = carRepository.findById("test-id");
        assertNotNull(foundCar);
        assertEquals("test-id", foundCar.getCarId());
        assertEquals("Toyota Camry", foundCar.getCarName());
        assertEquals("Black", foundCar.getCarColor());
    }
    
    @Test
    void testFindByIdNonExistent() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdate() {
        // Create initial car
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Camry");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        carRepository.create(car);

        // Create updated car with same ID
        Car updatedCar = new Car();
        updatedCar.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedCar.setCarName("Toyota Camry Updated");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(20);
        
        // Perform update
        Car result = carRepository.update(updatedCar);
        
        // Verify results
        assertNotNull(result);
        assertEquals(updatedCar.getCarName(), result.getCarName());
        assertEquals(updatedCar.getCarColor(), result.getCarColor());
        assertEquals(updatedCar.getCarQuantity(), result.getCarQuantity());
        
        // Also verify through findAll
        Iterator<Car> carIterator = carRepository.findAll();
        Car savedCar = carIterator.next();
        assertEquals(updatedCar.getCarName(), savedCar.getCarName());
        assertEquals(updatedCar.getCarColor(), savedCar.getCarColor());
        assertEquals(updatedCar.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testUpdateSecondCar() {
        // Create first car
        Car car1 = new Car();
        car1.setCarId("26fc7ab5-8e80-44d7-9fdc-2a804e1ede9f");
        car1.setCarName("First Car");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);
        carRepository.create(car1);

        // Create second car
        Car car2 = new Car();
        car2.setCarId("b6104af2-8a6e-40b8-aec5-396cdae48471");
        car2.setCarName("Second Car");
        car2.setCarColor("Green");
        car2.setCarQuantity(10);
        carRepository.create(car2);

        // Update second car
        Car updatedCar = new Car();
        updatedCar.setCarId("b6104af2-8a6e-40b8-aec5-396cdae48471");
        updatedCar.setCarName("Updated Second Car");
        updatedCar.setCarColor("Yellow");
        updatedCar.setCarQuantity(15);
        
        Car result = carRepository.update(updatedCar);
        
        // Verify the update
        assertNotNull(result);
        assertEquals(updatedCar.getCarName(), result.getCarName());
        assertEquals(updatedCar.getCarColor(), result.getCarColor());
        assertEquals(updatedCar.getCarQuantity(), result.getCarQuantity());
        
        // Verify first car remained unchanged
        Iterator<Car> carIterator = carRepository.findAll();
        Car firstCar = carIterator.next();
        assertEquals("First Car", firstCar.getCarName());
        assertEquals("Red", firstCar.getCarColor());
        assertEquals(5, firstCar.getCarQuantity());
    }

    @Test
    void testUpdateNonExistentCar() {
        Car nonExistentCar = new Car();
        nonExistentCar.setCarId("non-existent-id");
        nonExistentCar.setCarName("Non Existent Car");
        nonExistentCar.setCarColor("Black");
        nonExistentCar.setCarQuantity(100);
        
        Car result = carRepository.update(nonExistentCar);
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Create a car
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Camry");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        carRepository.create(car);

        // Verify car exists
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());

        // Delete the car
        carRepository.delete(car.getCarId());

        // Verify car no longer exists
        carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentCar() {
        // Delete non-existent car
        carRepository.delete("non-existent-id");
        
        // Verify no cars exist
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }
    
    @Test
    void testCreateMultipleDeleteOne() {
        // Create cars
        Car car1 = new Car();
        car1.setCarId("id-1");
        car1.setCarName("Car 1");
        car1.setCarColor("Red");
        car1.setCarQuantity(10);
        carRepository.create(car1);
        
        Car car2 = new Car();
        car2.setCarId("id-2");
        car2.setCarName("Car 2");
        car2.setCarColor("Blue");
        car2.setCarQuantity(20);
        carRepository.create(car2);
        
        // Delete one car
        carRepository.delete("id-1");
        
        // Verify only car2 exists
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car remainingCar = carIterator.next();
        assertEquals("id-2", remainingCar.getCarId());
        assertFalse(carIterator.hasNext());
    }
    
    @Test
    void testCreateWithIdGenerator() {
        // Set up the mock to return a specific ID
        when(idGeneratorService.generateId()).thenReturn("test-generated-id");
        
        Car car = new Car();
        car.setCarName("Test Car");
        car.setCarColor("Silver");
        car.setCarQuantity(5);
        
        Car createdCar = carRepository.create(car);
        
        // Verify the ID generator was used
        assertEquals("test-generated-id", createdCar.getCarId());
        verify(idGeneratorService, times(1)).generateId();
    }
}