package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Identifiable;
import id.ac.ui.cs.advprog.eshop.service.IdGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbstractItemRepositoryTest {

    @Mock
    private IdGeneratorService idGeneratorService;

    private TestRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TestRepository(idGeneratorService);
    }

    @Test
    void testFindByIdWithNullId() {
        // This should cover the missed branch in findById
        TestItem item = repository.findById(null);
        assertNull(item);
    }

    // A concrete implementation of AbstractItemRepository for testing
    private static class TestRepository extends AbstractItemRepository<TestItem> {
        public TestRepository(IdGeneratorService idGeneratorService) {
            super(idGeneratorService);
        }

        @Override
        public TestItem update(TestItem item) {
            return null; // Not needed for this test
        }
    }

    // A concrete implementation of Identifiable for testing
    private static class TestItem implements Identifiable {
        private String id;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return null;
        }

        public void setName(String name) {
            // Not needed
        }

        public Integer getQuantity() {
            return null;
        }

        public void setQuantity(Integer quantity) {
            // Not needed
        }
    }
}