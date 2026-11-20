package src.service;

import src.model.Order;

public interface OrderService {
    Order createOrder(double amount);
    Order getOrder(int id);
    String getOrderStatus(int id);
}