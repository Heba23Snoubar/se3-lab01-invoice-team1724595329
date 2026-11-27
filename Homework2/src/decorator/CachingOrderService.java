package src.decorator;

import src.model.Order;
import src.service.OrderService;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Decorator that caches read results ({@code getOrder}, {@code getOrderStatus})
 * and invalidates affected cache entries on updates and deletions.
 * Uses defensive copies of {@link Order} so external mutations cannot bypass cache invalidation.
 */
public class CachingOrderService extends OrderServiceDecorator {

    private final Map<Integer, Order> orderCache = new HashMap<>();
    private final Map<Integer, String> statusCache = new HashMap<>();

    public CachingOrderService(OrderService wrappedService) {
        super(wrappedService);
    }

    @Override
    public Order getOrder(int id) {
        Order cached = orderCache.get(id);
        if (cached != null) {
            System.out.println("[CACHE] Cache HIT for getOrder(id=" + id + ")");
            return copyOrder(cached);
        }

        System.out.println("[CACHE] Cache MISS for getOrder(id=" + id + ")");
        Order order = super.getOrder(id);
        if (order != null) {
            orderCache.put(id, copyOrder(order));
            return copyOrder(order);
        }
        return null;
    }

    @Override
    public String getOrderStatus(int id) {
        String cachedStatus = statusCache.get(id);
        if (cachedStatus != null) {
            System.out.println("[CACHE] Cache HIT for getOrderStatus(id=" + id + ")");
            return cachedStatus;
        }

        System.out.println("[CACHE] Cache MISS for getOrderStatus(id=" + id + ")");
        String status = super.getOrderStatus(id);
        if (status != null) {
            statusCache.put(id, status);
        }
        return status;
    }

    @Override
    public Order updateOrder(Order order) {
        Order updated = super.updateOrder(order);
        invalidate(updated.getId());
        System.out.println("[CACHE] Invalidated cache for order #" + updated.getId());
        return copyOrder(updated);
    }

    @Override
    public boolean deleteOrder(int id) {
        boolean deleted = super.deleteOrder(id);
        boolean hadCachedEntry = invalidate(id);
        if (deleted || hadCachedEntry) {
            System.out.println("[CACHE] Evicted order #" + id + " from cache");
        }
        return deleted;
    }

    private boolean invalidate(int id) {
        boolean removedOrder = orderCache.remove(id) != null;
        boolean removedStatus = statusCache.remove(id) != null;
        return removedOrder || removedStatus;
    }

    private Order copyOrder(Order source) {
        Order copy = new Order(source.getId(), source.getAmount());
        copy.setState(source.getState());
        copy.setPaymentStrategy(source.getPaymentStrategy());
        return copy;
    }
}
