package src.state;

import src.model.Order;

/** TEMPORARY stub from Foundation so everything compiles.
 *  Heba-Abbas replaces this file in the State task. */
public class PendingState implements OrderState {
    @Override public void pay(Order order)     { System.out.println("[TODO] PendingState.pay"); }
    @Override public void ship(Order order)    { System.out.println("[TODO] PendingState.ship"); }
    @Override public void deliver(Order order) { System.out.println("[TODO] PendingState.deliver"); }
    @Override public void cancel(Order order)  { System.out.println("[TODO] PendingState.cancel"); }
    @Override public String getName()          { return "Pending"; }
}