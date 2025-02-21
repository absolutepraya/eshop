package id.ac.ui.cs.advprog.eshop;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {
    @Test
    void contextLoads() {
        /*
         * This test method is intentionally empty because it only verifies that
         * the Spring application context loads successfully. The @SpringBootTest
         * annotation handles the actual testing of context loading.
         */
    }

    @Test
    void testMain() {
        // Verify that main method executes without throwing exceptions
        assertDoesNotThrow(() -> {
            EshopApplication.main(new String[] {});
        }, "Main method should execute without throwing exceptions");
    }
}