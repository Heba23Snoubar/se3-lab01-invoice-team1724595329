package src.decorator;

import src.model.Order;
import src.service.OrderService;

import java.util.Objects;

/**
 * Base Decorator for {@link OrderService}.
 * Holds a reference to a wrapped {@link OrderService} and delegates all calls to it.
 */
public abstract class OrderServiceDecorator implements OrderService {

    protected final OrderService wrappedService;

    protected OrderServiceDecorator(OrderService wrappedService) {
        this.wrappedService = Objects.requireNonNull(wrappedService, "wrappedService must not be null");
    }

    @Override
    public Order createOrder(double amount) {
        return wrappedService.createOrder(amount);
    }

    @Override
    public Order getOrder(int id) {
        return wrappedService.getOrder(id);
    }

    @Override
    public String getOrderStatus(int id) {
        return wrappedService.getOrderStatus(id);
    }

    @Override
    public Order updateOrder(Order order) {
        return wrappedService.updateOrder(order);
    }

    @Override
    public boolean deleteOrder(int id) {
        return wrappedService.deleteOrder(id);
    }
}
