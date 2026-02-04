package com.trade.order_management.helper;

import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.repository.OrderBook;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PriceCalculatorHelper {

    private final OrderBook orderBook;
    public PriceCalculatorHelper(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public int calculatePrice(String symbol, Side side, int amount){
        return Side.BUY == side
                ? calculateBuyPrice(symbol, amount)
                : calculateSellPrice(symbol, amount);
    }

    private int calculateBuyPrice(String symbol, int amount){
        List<Order> sortedOrders = orderBook.getOrdersBySymbol(symbol).stream()
                .filter(order -> order.side() == Side.BUY)
                .toList();
        return this.calculate(amount, sortedOrders);
    }

    private int calculateSellPrice(String symbol, int amount){
        List<Order> sortedOrders = orderBook.getOrdersBySymbol(symbol).stream()
                .filter(order -> order.side() == Side.SELL)
                .sorted(Comparator.comparing(Order::price).reversed()).toList();
        return this.calculate(amount, sortedOrders);
    }

    private int calculate(int amount, List<Order> sortedOrders){
        int totalPrices = 0;
        int totalAmount = amount;
        for (Order order: sortedOrders){
            if(totalAmount == 0){
                return totalPrices;
            }
            int modValue = totalAmount % order.amount();
            if(totalAmount >= order.amount()){
                totalPrices = (totalAmount-modValue)*order.price();
                totalAmount = totalAmount - order.amount();
            }else{
                totalPrices = totalPrices + modValue*order.price();
                totalAmount = 0;
            }
        }
        return totalPrices;
    }
}
