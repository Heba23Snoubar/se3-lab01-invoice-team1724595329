package src.state;

import src.model.Order;

/**
 * State pattern - ConcreteState: the order has been paid, waiting to be shipped.
 *
 * Transitions from Paid:
 *   ship()    -> ShippedState
 *   cancel()  -> CancelledState  (allowed: the goods have not left the store yet)
 *   pay()     -> NOT allowed (error: already paid, no second charge)
 *   deliver() -> NOT allowed (error: must be shipped first)
 *
 * Full table: see Homework2/README.md (State section).
 */
public class PaidState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot pay - the order is already paid. Status stays Paid.");
    }

    @Override
    public void ship(Order order) {
        order.setState(new ShippedState());
        System.out.println("[OK] Order #" + order.getId() + ": Paid -> Shipped");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot deliver - the order has not been shipped yet. Status stays Paid.");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
        System.out.println("[OK] Order #" + order.getId() + ": Paid -> Cancelled");
    }

    @Override
    public String getName() { return "Paid"; }
}