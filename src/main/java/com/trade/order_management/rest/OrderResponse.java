package com.trade.order_management.rest;

import lombok.Data;

@Data
public class OrderResponse<T> {
    private final T data;
    public OrderResponse(T data) {
        this.data = data;
    }
}
