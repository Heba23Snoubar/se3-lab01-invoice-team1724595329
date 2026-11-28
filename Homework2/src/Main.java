package src;

import src.decorator.CachingOrderService;
import src.decorator.LoggingOrderService;
import src.facade.CheckoutFacade;
import src.model.Order;
import src.payment.CardPayment;
import src.payment.CashOnDelivery;
import src.payment.PayPalAdapter;
import src.payment.PaymentStrategy;
import src.service.BasicOrderService;
import src.service.OrderService;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Console program that ties the five patterns together.
 *
 *   State     : Order moves Pending -> Paid -> Shipped -> Delivered (or Cancelled).
 *   Strategy  : option 2 picks the payment strategy of the order (Card / Cash / PayPal).
 *   Adapter   : PayPal is just another PaymentStrategy (PayPalAdapter wraps the external lib).
 *   Facade    : option 3 calls CheckoutFacade.placeOrder() - inventory, payment, shipping, notification.
 *   Decorator : the order service is wrapped with caching and logging (see the [LOG] / [CACHE] lines).
 *
 * Decision: option 3 ("pay") runs the whole purchase through the facade, so a successful
 * checkout leaves the order Shipped. Option 4 (ship) and 5 (deliver) go straight through
 * the State pattern: 5 completes the life cycle, and 4 is mainly a way to see State refusing
 * a transition that is not allowed (an order that is already shipped cannot be shipped again).
 *
 * Run with "--echo" to print the typed lines (useful when the input is piped from a file).
 */
public class Main {

    private static final List<String> PAYMENT_NAMES =
            List.of("Card", "Cash on delivery", "PayPal");
    private static final List<Supplier<PaymentStrategy>> PAYMENT_STRATEGIES =
            List.of(CardPayment::new, CashOnDelivery::new, PayPalAdapter::new);

    private static Scanner in;
    private static boolean echo;
    private static OrderService orders;
    private static CheckoutFacade checkout;
    private static int currentOrderId = -1;

    public static void main(String[] args) {
        echo = args.length > 0 && "--echo".equals(args[0]);
        in = new Scanner(System.in);

        // Decorator: logging on the outside, caching in the middle, the basic service inside.
        orders = new LoggingOrderService(new CachingOrderService(new BasicOrderService()));
        // Facade: Main only knows this one object, not the services behind it.
        checkout = new CheckoutFacade();

        boolean running = true;
        while (running) {
            printMenu();
            String line = readLine("Your choice: ");
            if (line == null) {          // input ended (Ctrl+D or end of a piped file)
                break;
            }
            try {
                switch (parseInt(line)) {
                    case 1: createOrder(); break;
                    case 2: choosePayment(); break;
                    case 3: pay(); break;
                    case 4: runOnOrder(Order::ship); break;
                    case 5: runOnOrder(Order::deliver); break;
                    case 6: runOnOrder(Order::cancel); break;
                    case 7: showStatus(); break;
                    case 8: running = false; break;
                    default: System.out.println("Invalid choice, please type a number from 1 to 8.");
                }
            } catch (RuntimeException e) {
                // Whatever goes wrong inside an action, the program keeps running.
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    // ---------- menu actions ----------

    private static void createOrder() {
        String text = readLine("Order amount: ");
        if (text == null) return;
        double amount;
        try {
            amount = Double.parseDouble(text.replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount: please type a number such as 100 or 49.5.");
            return;
        }
        try {
            Order order = orders.createOrder(amount);
            currentOrderId = order.getId();
            System.out.println("Created " + order);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot create the order: " + e.getMessage());
        }
    }

    private static void choosePayment() {
        Order order = selectOrder();
        if (order == null) return;

        System.out.println("Payment methods:");
        for (int i = 0; i < PAYMENT_NAMES.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + PAYMENT_NAMES.get(i));
        }
        String line = readLine("Payment method: ");
        if (line == null) return;
        int index = parseInt(line) - 1;
        if (index < 0 || index >= PAYMENT_STRATEGIES.size()) {
            System.out.println("Invalid payment method, nothing changed.");
            return;
        }
        order.setPaymentStrategy(PAYMENT_STRATEGIES.get(index).get());
        save(order);
        System.out.println("Order #" + order.getId() + " will be paid by: " + PAYMENT_NAMES.get(index));
    }

    /** Option 3: the full purchase path, through the facade (one call). */
    private static void pay() {
        Order order = selectOrder();
        if (order == null) return;
        boolean placed = checkout.placeOrder(order);
        save(order);
        System.out.println(placed ? "Purchase completed." : "Purchase was not completed.");
    }

    /** Options 4, 5, 6: a single transition, driven by the State pattern. */
    private static void runOnOrder(Consumer<Order> action) {
        Order order = selectOrder();
        if (order == null) return;
        action.accept(order);
        save(order);
    }

    private static void showStatus() {
        Order order = selectOrder();
        if (order == null) return;
        System.out.println("Status of order #" + order.getId() + ": " + orders.getOrderStatus(order.getId()));
        System.out.println(order);
    }

    // ---------- helpers ----------

    /** Asks for an order id (Enter = the current order) and loads it from the service. */
    private static Order selectOrder() {
        if (currentOrderId < 0) {
            System.out.println("There is no order yet, create one first (option 1).");
            return null;
        }
        String line = readLine("Order id [Enter = #" + currentOrderId + "]: ");
        if (line == null) return null;
        int id = currentOrderId;
        if (!line.isEmpty()) {
            id = parseInt(line);
            if (id < 0) {
                System.out.println("Invalid order id.");
                return null;
            }
        }
        Order order = orders.getOrder(id);
        if (order == null) {
            System.out.println("Order #" + id + " was not found.");
            return null;
        }
        currentOrderId = id;
        return order;
    }

    /** The service keeps copies of the orders, so every change has to be saved back. */
    private static void save(Order order) {
        orders.updateOrder(order);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Order Management =====");
        System.out.println("1) Create order");
        System.out.println("2) Choose payment method");
        System.out.println("3) Pay (full checkout: stock -> payment -> shipping -> notification)");
        System.out.println("4) Ship");
        System.out.println("5) Deliver");
        System.out.println("6) Cancel");
        System.out.println("7) Show status");
        System.out.println("8) Exit");
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) {
            System.out.println();
            return null;
        }
        String line = in.nextLine().trim();
        if (echo) {
            System.out.println(line);
        }
        return line;
    }

    /** Returns the number typed, or -1 when the text is not a valid non-negative number. */
    private static int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}