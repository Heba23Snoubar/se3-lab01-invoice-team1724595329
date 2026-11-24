package src.state;

import src.model.Order;

/**
 * State pattern - ConcreteState: FINAL state, the order was cancelled.
 *
 * Transitions from Cancelled: none. Every operation is rejected with an error
 * and the order stays Cancelled.
 *   pay() / ship() / deliver() / cancel() -> NOT allowed
 *
 * Full table: see Homework2/README.md (State section).
 */
public class CancelledState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot pay - the order is cancelled. Status stays Cancelled.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot ship - the order is cancelled. Status stays Cancelled.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot deliver - the order is cancelled. Status stays Cancelled.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot cancel - the order is already cancelled. Status stays Cancelled.");
    }

    @Override
    public String getName() { return "Cancelled"; }
}