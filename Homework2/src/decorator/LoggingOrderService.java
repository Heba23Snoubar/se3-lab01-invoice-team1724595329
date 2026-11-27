package src.decorator;

import src.model.Order;
import src.service.OrderService;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * Concrete Decorator that logs operation name, execution time, result, and errors.
 */
public class LoggingOrderService extends OrderServiceDecorator {

    public LoggingOrderService(OrderService wrappedService) {
        super(wrappedService);
    }

    @Override
    public Order createOrder(double amount) {
        String operation = String.format(Locale.US, "createOrder(amount=%.2f)", amount);
        return executeWithLogging(operation, () -> super.createOrder(amount));
    }

    @Override
    public Order getOrder(int id) {
        return executeWithLogging("getOrder(id=" + id + ")", () -> super.getOrder(id));
    }

    @Override
    public String getOrderStatus(int id) {
        return executeWithLogging("getOrderStatus(id=" + id + ")", () -> super.getOrderStatus(id));
    }

    @Override
    public Order updateOrder(Order order) {
        String target = order == null ? "null" : "id=" + order.getId();
        return executeWithLogging("updateOrder(" + target + ")", () -> super.updateOrder(order));
    }

    @Override
    public boolean deleteOrder(int id) {
        return executeWithLogging("deleteOrder(id=" + id + ")", () -> super.deleteOrder(id));
    }

    private <T> T executeWithLogging(String operation, Supplier<T> action) {
        System.out.println("[LOG] Calling " + operation);
        long startNs = System.nanoTime();
        try {
            T result = action.get();
            double elapsedMs = (System.nanoTime() - startNs) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[LOG] Completed %s in %.3f ms | Result: %s%n",
                    operation, elapsedMs, formatResult(result));
            return result;
        } catch (RuntimeException ex) {
            double elapsedMs = (System.nanoTime() - startNs) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[LOG] Failed %s in %.3f ms | Error: %s: %s%n",
                    operation, elapsedMs, ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    private String formatResult(Object result) {
        return result == null ? "null" : result.toString();
    }
}
