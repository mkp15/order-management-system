package com.trade.order_management.repository;

import com.trade.order_management.domain.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderBook {

    private Map<String, TreeSet<Order>> ordersBySymbol = new HashMap<>();

    public void addOrder(Order order){
        // Assumption here that Order cannot be null
        if(this.ordersBySymbol.containsKey(order.symbol())){
            this.ordersBySymbol.get(order.symbol()).add(order);
        }
        else{
            TreeSet<Order> sorted = new TreeSet<>(Comparator.comparing(Order::price));
            sorted.add(order);
            this.ordersBySymbol.put(order.symbol(), sorted);
        }

    }

    public void removeOrder(int orderId){
        Optional<Order> matchFound = this.ordersBySymbol.values()
                .stream().flatMap(Collection::stream)
                .filter(order -> order.orderId() == orderId)
                .findFirst();
        matchFound.ifPresent(orderToBeDeleted -> {
            for (Map.Entry<String, TreeSet<Order>> entry : ordersBySymbol.entrySet()) {
                TreeSet<Order> orders = entry.getValue();
                orders.remove(orderToBeDeleted);
            }
        });
    }

    public TreeSet<Order> getOrdersBySymbol(String symbol){
        return this.ordersBySymbol.getOrDefault(symbol, new TreeSet<>());
    }

    public long totalOrder(){
        return this.ordersBySymbol.values()
                .stream()
                .mapToLong(Collection::size)
                .sum();
    }
}
