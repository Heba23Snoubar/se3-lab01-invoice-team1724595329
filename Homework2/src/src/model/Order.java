package src.model;


import src.payment.PaymentStrategy;
import src.state.*;

/** State-pattern Context: holds the current state and delegates every
 *  operation to it. It also holds the chosen payment strategy (Strategy pattern). */
public class Order {
    private final int id;
    private final double amount;
    private OrderState state;
    private PaymentStrategy paymentStrategy;

    public Order(int id, double amount) {
        this.id = id;
        this.amount = amount;
        this.state = new PendingState(); // every order starts as Pending
    }

    // --- delegation to the current state (no if/else here) ---
    public void pay()     { state.pay(this); }
    public void ship()    { state.ship(this); }
    public void deliver() { state.deliver(this); }
    public void cancel()  { state.cancel(this); }

    // --- used by the states to move the order forward ---
    public void setState(OrderState state) { this.state = state; }
    public OrderState getState()           { return state; }
    public String getStatusName()          { return state.getName(); }

    // --- payment strategy (can be switched at runtime) ---
    public void setPaymentStrategy(PaymentStrategy strategy) { this.paymentStrategy = strategy; }
    public PaymentStrategy getPaymentStrategy()              { return paymentStrategy; }

    public int getId()        { return id; }
    public double getAmount() { return amount; }


    public double processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Set a payment strategy before processing payment.");
        }
        return paymentStrategy.pay(amount);
    }

    @Override
    public String toString() {
        return "Order #" + id + " | amount=" + amount + " | status=" + state.getName();
    }
}