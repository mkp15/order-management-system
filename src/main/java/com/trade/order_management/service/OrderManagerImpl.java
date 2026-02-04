package com.trade.order_management.service;

import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.helper.PriceCalculatorHelper;
import com.trade.order_management.repository.OrderBook;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderManagerImpl implements OrderManager {

    private final OrderBook orderBook;
    private final PriceCalculatorHelper priceCalculatorHelper;

    public OrderManagerImpl(OrderBook orderBook, PriceCalculatorHelper priceCalculatorHelper) {
        this.orderBook = orderBook;
        this.priceCalculatorHelper = priceCalculatorHelper;
    }

    @Override
    public void addOrder(int orderId, String symbol, Side side, int amount, int price) {
        this.orderBook.addOrder(new Order(orderId, symbol, side, amount, price));
    }

    @Override
    public void removeOrder(int orderId) {
       this.orderBook.removeOrder(orderId);
    }

    @Override
    public int calculatePrice(String symbol, Side side, int amount) {
        // In instructions it says should return with decimal 400.00 but in interface contract int
        return priceCalculatorHelper.calculatePrice(symbol, side, amount);
    }

    @Override
    public void placeOrder(String symbol, Side side, int amount) {
        Stream<Order> orderStream = orderBook.getOrdersBySymbol(symbol).stream()
                .filter(order -> order.side() == side);
        List<Order> sortedOrders = Side.BUY == side
                ? orderStream.toList()
                : orderStream.sorted(Comparator.comparing(Order::price).reversed()).toList();
        int totalAmount = amount;
        for (Order order: sortedOrders){
            if(totalAmount == 0){
                break;
            }
            int modValue = totalAmount % order.amount();
            if(totalAmount >= order.amount()){
                totalAmount = totalAmount - order.amount();
                this.orderBook.removeOrder(order.orderId());
            }else{
                totalAmount = 0;
                this.orderBook.removeOrder(order.orderId());
                this.orderBook.addOrder(new Order(order.orderId(), order.symbol(),order.side(),order.amount()-modValue, order.price() ));
            }
        }
    }
}
