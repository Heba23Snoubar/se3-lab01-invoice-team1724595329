package src.state;

import src.model.Order;

/**
 * State pattern - ConcreteState: FINAL state, the order reached the customer.
 *
 * Transitions from Delivered: none. Every operation is rejected with an error
 * and the order stays Delivered.
 *   pay() / ship() / deliver() / cancel() -> NOT allowed
 *
 * Full table: see Homework2/README.md (State section).
 */
public class DeliveredState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot pay - the order is already delivered. Status stays Delivered.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot ship - the order is already delivered. Status stays Delivered.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot deliver - the order is already delivered. Status stays Delivered.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot cancel - the order has already been delivered "
                + "(cancel is only allowed in Pending or Paid). Status stays Delivered.");
    }

    @Override
    public String getName() { return "Delivered"; }
}