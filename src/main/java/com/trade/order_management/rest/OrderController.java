package com.trade.order_management.rest;

import com.trade.order_management.domain.Order;
import com.trade.order_management.service.OrderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
@Slf4j
public class OrderController {

    private final OrderManager orderManager;

    @Autowired
    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PostMapping
    public ResponseEntity<String> addOder(@RequestBody Order order){
        orderManager.addOrder(order.orderId(), order.symbol(), order.side(), order.amount(), order.price());
        log.info("Order got added successfully to order booking");
        return ResponseEntity.ok("Order got added successfully to order booking");
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<String> deleteOrder(@RequestBody int orderId){
        orderManager.removeOrder(orderId);
        return ResponseEntity.ok(String.format("Order ID: %d deleted successfully", orderId));
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<OrderResponse<String>> placeOrder(@RequestBody Order order){
        orderManager.placeOrder(order.symbol(), order.side(), order.price());
        return ResponseEntity.ok(new OrderResponse<>("Order placed successfully"));
    }

    @PostMapping(path = "/price")
    public ResponseEntity<OrderResponse<Integer>> calculatedPrice(@RequestBody Order order){
        int totalPrice = orderManager.calculatePrice(order.symbol(), order.side(), order.price());
        log.info("TotalPrice calculated successfully");
        return ResponseEntity.ok(new OrderResponse<>(totalPrice));
    }
}
