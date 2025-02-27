package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IdGeneratorService {
    
    /**
     * Generates a unique ID using UUID
     * @return A string representation of a UUID
     */
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}