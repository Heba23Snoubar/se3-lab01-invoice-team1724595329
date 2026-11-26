package src.service;

import src.model.Order;

/** Facade pattern - subsystem class: notifies the customer. */
public class NotificationService {

    public void notifyCustomer(Order order, String message) {
        System.out.println("[Notification] Order #" + order.getId() + ": " + message);
    }
}