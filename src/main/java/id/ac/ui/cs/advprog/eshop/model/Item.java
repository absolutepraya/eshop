package id.ac.ui.cs.advprog.eshop.model;

/**
 * Base interface for all items in the shop
 * Combines multiple specific interfaces
 */
public interface Item extends Identifiable, Named, Quantifiable {
    // No additional methods needed as we're composing from other interfaces
}