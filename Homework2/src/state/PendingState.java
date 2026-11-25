package src.state;

import src.model.Order;
import src.payment.PaymentStrategy;

/**
 * State pattern - ConcreteState: the order has been created but not paid yet.
 *
 * Transitions from Pending:
 *   pay()     -> PaidState       (only if a payment strategy was chosen; the
 *                                 payment is executed BEFORE the transition)
 *   cancel()  -> CancelledState
 *   ship()    -> NOT allowed (error, stays Pending)
 *   deliver() -> NOT allowed (error, stays Pending)
 *
 * Full table: see Homework2/README.md (State section).
 */
public class PendingState implements OrderState {

    @Override
    public void pay(Order order) {
        // 1) Validate first: no strategy => no payment, no transition.
        PaymentStrategy strategy = order.getPaymentStrategy();
        if (strategy == null) {
            System.out.println("[ERROR] Order #" + order.getId()
                    + ": cannot pay - no payment method selected. "
                    + "Choose a payment strategy first. Status stays Pending.");
            return;
        }
        // 2) Charge the customer.
        strategy.pay(order.getAmount());
        // 3) Only now move to the next state.
        order.setState(new PaidState());
        System.out.println("[OK] Order #" + order.getId() + ": Pending -> Paid");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot ship - the order is not paid yet. Status stays Pending.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[ERROR] Order #" + order.getId()
                + ": cannot deliver - the order has not been paid or shipped. Status stays Pending.");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
        System.out.println("[OK] Order #" + order.getId() + ": Pending -> Cancelled");
    }

    @Override
    public String getName() { return "Pending"; }
}