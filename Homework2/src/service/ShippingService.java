package src.service;

import src.model.Order;

/** Facade pattern - subsystem class: arranges the shipment of an order. */
public class ShippingService {

    public void dispatch(Order order) {
        System.out.println("[Shipping] Order #" + order.getId()
                + ": package handed to the courier.");
    }
}