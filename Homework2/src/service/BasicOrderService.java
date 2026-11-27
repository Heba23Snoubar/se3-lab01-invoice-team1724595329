package src.service;

import src.model.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Core in-memory implementation of {@link OrderService}.
 * Responsible only for managing and storing orders without logging or caching.
 */
public class BasicOrderService implements OrderService {

    private final Map<Integer, Order> orders = new HashMap<>();
    private int nextId = 1;

    @Override
    public Order createOrder(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Order amount must be positive: " + amount);
        }
        Order order = new Order(nextId++, amount);
        orders.put(order.getId(), copyOrder(order));
        return copyOrder(order);
    }

    @Override
    public Order getOrder(int id) {
        Order order = orders.get(id);
        return order == null ? null : copyOrder(order);
    }

    @Override
    public String getOrderStatus(int id) {
        Order order = orders.get(id);
        return order == null ? null : order.getStatusName();
    }

    @Override
    public Order updateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null.");
        }
        if (!orders.containsKey(order.getId())) {
            throw new IllegalArgumentException("Order #" + order.getId() + " not found.");
        }
        if (order.getState() == null) {
            throw new IllegalArgumentException("Order state must not be null.");
        }
        Order stored = copyOrder(order);
        orders.put(stored.getId(), stored);
        return copyOrder(stored);
    }

    @Override
    public boolean deleteOrder(int id) {
        return orders.remove(id) != null;
    }

    private Order copyOrder(Order source) {
        Order copy = new Order(source.getId(), source.getAmount());
        copy.setState(source.getState());
        copy.setPaymentStrategy(source.getPaymentStrategy());
        return copy;
    }
}
