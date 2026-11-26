package src.service;

import src.model.Order;

/**
 * Facade pattern - subsystem class: keeps a simple in-memory stock counter.
 * Each order consumes one unit. Returns true/false so the stock check is real.
 */
public class InventoryService {

    private int availableUnits;

    public InventoryService() {
        this(10);
    }

    public InventoryService(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    /** Checks the stock and reserves one unit. Returns false if out of stock. */
    public boolean reserve(Order order) {
        if (availableUnits <= 0) {
            System.out.println("[Inventory] Order #" + order.getId()
                    + ": out of stock, cannot reserve.");
            return false;
        }
        availableUnits--;
        System.out.println("[Inventory] Order #" + order.getId()
                + ": item reserved. Units left: " + availableUnits);
        return true;
    }

    /** Gives the reserved unit back (used when a later step fails). */
    public void release(Order order) {
        availableUnits++;
        System.out.println("[Inventory] Order #" + order.getId()
                + ": reservation released. Units left: " + availableUnits);
    }

    public int getAvailableUnits() {
        return availableUnits;
    }
}