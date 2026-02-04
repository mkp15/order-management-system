package com.trade.order_management.domain;

import com.trade.order_management.enums.Side;
import lombok.Data;

public record Order(int orderId,String symbol, Side side,int amount,Integer price) {

}
