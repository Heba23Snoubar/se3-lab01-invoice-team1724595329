package src;

import src.decorator.CachingOrderService;
import src.decorator.LoggingOrderService;
import src.facade.CheckoutFacade;
import src.model.Order;
import src.payment.CardPayment;
import src.payment.CashOnDelivery;
import src.payment.PayPalAdapter;
import src.service.BasicOrderService;
import src.service.OrderService;

public class Main {
    public static void main(String[] args) {
        Order order = new Order(1, 100.0);

        order.setPaymentStrategy(new CardPayment());
        double cardTotal = order.processPayment();
        System.out.printf("Card total: %.2f%n%n", cardTotal);

        order.setPaymentStrategy(new CashOnDelivery());
        double cashTotal = order.processPayment();
        System.out.printf("Cash-on-delivery total: %.2f%n", cashTotal);

        runDecoratorDemo();
    }

    private static void runDecoratorDemo() {
        System.out.println("\n=== OrderService Decorator Demo (Logging + Caching) ===");
        OrderService orderService = new LoggingOrderService(
                new CachingOrderService(
                        new BasicOrderService()
                )
        );

        Order created = orderService.createOrder(250.0);
        int orderId = created.getId();

        // First read -> Cache MISS; second read -> Cache HIT
        Order firstRead = orderService.getOrder(orderId);
        orderService.getOrder(orderId);

        // Process checkout (Strategy + Adapter + State + Facade), then update via OrderService
        firstRead.setPaymentStrategy(new PayPalAdapter());
        CheckoutFacade checkoutFacade = new CheckoutFacade();
        checkoutFacade.placeOrder(firstRead);
        orderService.updateOrder(firstRead);

        // Read after update -> Cache MISS with updated status, then Cache HIT
        orderService.getOrder(orderId);
        orderService.getOrderStatus(orderId);
        orderService.getOrderStatus(orderId);

        // Delete order -> cache evicted, subsequent read returns null (Cache MISS)
        orderService.deleteOrder(orderId);
        orderService.getOrder(orderId);
    }
}