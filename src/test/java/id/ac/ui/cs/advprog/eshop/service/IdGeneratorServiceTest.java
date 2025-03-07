package id.ac.ui.cs.advprog.eshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorServiceTest {

    private IdGeneratorService idGeneratorService;

    @BeforeEach
    void setUp() {
        idGeneratorService = new IdGeneratorService();
    }

    @Test
    void generateId_shouldReturnUniqueId() {
        // Call the method
        String id = idGeneratorService.generateId();
        
        // Verify the id is not null and has the expected format
        assertNotNull(id);
        
        // Verify the uniqueness by generating another id
        String anotherId = idGeneratorService.generateId();
        assertNotNull(anotherId);
        
        // The two generated ids should be different
        assertNotEquals(id, anotherId);
        
        // Additional check: UUID format has 36 characters (including hyphens)
        assertEquals(36, id.length());
    }
}