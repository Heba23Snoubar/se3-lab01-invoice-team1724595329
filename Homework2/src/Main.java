package src;

import src.model.Order;
import src.payment.CardPayment;
import src.payment.CashOnDelivery;

public class Main {
    public static void main(String[] args) {
        Order order = new Order(1, 100.0);

        order.setPaymentStrategy(new CardPayment());
        double cardTotal = order.processPayment();
        System.out.printf("Card total: %.2f%n%n", cardTotal);

        order.setPaymentStrategy(new CashOnDelivery());
        double cashTotal = order.processPayment();
        System.out.printf("Cash-on-delivery total: %.2f%n", cashTotal);
    }
}