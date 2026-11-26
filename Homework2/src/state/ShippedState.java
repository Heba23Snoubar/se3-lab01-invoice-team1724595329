package src.state;

import src.model.Order;

/**
 * State pattern - ConcreteState: the order is on its way to the customer.
 *
 * Transitions from Shipped:
 *   deliver() -> DeliveredState
 *   pay()     -> NOT allowed (error: already paid)
 *   ship()    -> NOT allowed (error: already shipped)
 *   cancel()  -> NOT allowed (error: cancellation is only allowed in Pending/Paid)
 *
 * Full table: see Homework2/README.md (State section).
 */
public class ShippedState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot pay - the order is already paid and shipped. Status stays Shipped.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot ship - the order is already shipped. Status stays Shipped.");
    }

    @Override
    public void deliver(Order order) {
        order.setState(new DeliveredState());
        System.out.println("[OK] Order #" + order.getId() + ": Shipped -> Delivered");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot cancel - the order has already been shipped "
                + "(cancel is only allowed in Pending or Paid). Status stays Shipped.");
    }

    @Override
    public String getName() { return "Shipped"; }
}