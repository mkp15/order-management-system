package com.trade.order_management.service;

import com.trade.order_management.enums.Side;

public interface OrderManager {
    void addOrder(int orderId, String symbol, Side side, int amount, int price);
    void removeOrder(int orderId);
    int calculatePrice(String symbol, Side side, int amount);
    void placeOrder(String symbol, Side side, int amount);
}
