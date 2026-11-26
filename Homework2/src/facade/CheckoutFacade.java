package src.facade;

import src.model.Order;
import src.service.InventoryService;
import src.service.NotificationService;
import src.service.ShippingService;

/**
 * Facade pattern: one simple entry point (placeOrder) that hides the
 * coordination of several subsystems.
 *
 *   Facade     : CheckoutFacade
 *   Subsystems : InventoryService, ShippingService, NotificationService,
 *                the payment strategy chosen in the order (via Order.pay()),
 *                and the order state (via Order.pay() / Order.ship()).
 *
 * Steps, in order: state check -> inventory -> payment -> shipping -> notification.
 * Failures never throw: the method prints the reason and returns false.
 */
public class CheckoutFacade {

    private static final String PENDING = "Pending";
    private static final String PAID = "Paid";
    private static final String SHIPPED = "Shipped";

    private final InventoryService inventory;
    private final ShippingService shipping;
    private final NotificationService notification;

    public CheckoutFacade() {
        this(new InventoryService(), new ShippingService(), new NotificationService());
    }

    public CheckoutFacade(InventoryService inventory,
                          ShippingService shipping,
                          NotificationService notification) {
        this.inventory = inventory;
        this.shipping = shipping;
        this.notification = notification;
    }

    /** @return true if the order was paid and shipped, false if any step failed. */
    public boolean placeOrder(Order order) {
        if (order == null) {
            System.out.println("[Checkout] ERROR: no order given.");
            return false;
        }
        System.out.println("[Checkout] Placing order #" + order.getId() + "...");

        // 0) The order must still be Pending (do not touch stock for cancelled/paid orders).
        if (!PENDING.equals(order.getStatusName())) {
            System.out.println("[Checkout] ERROR: order #" + order.getId()
                    + " is " + order.getStatusName() + ", checkout needs a Pending order.");
            return false;
        }

        // 1) Inventory check (reserve one unit).
        if (!inventory.reserve(order)) {
            System.out.println("[Checkout] FAILED: order #" + order.getId()
                    + " not placed (insufficient stock).");
            return false;
        }

        // 2) Payment, through the strategy chosen in the order. The state validates,
        //    charges and moves to Paid. pay() returns nothing, so we check the status.
        order.pay();
        if (!PAID.equals(order.getStatusName())) {
            inventory.release(order);   // give the reserved unit back
            System.out.println("[Checkout] FAILED: order #" + order.getId()
                    + " not placed (payment did not go through).");
            return false;
        }

        // 3) Shipping: the state validates and moves to Shipped, then the courier is called.
        order.ship();
        if (!SHIPPED.equals(order.getStatusName())) {
            System.out.println("[Checkout] FAILED: order #" + order.getId()
                    + " could not be shipped.");
            return false;
        }
        shipping.dispatch(order);

        // 4) Notification.
        notification.notifyCustomer(order, "your order has been paid and shipped.");

        System.out.println("[Checkout] SUCCESS: order #" + order.getId() + " is " + order.getStatusName());
        return true;
    }
}