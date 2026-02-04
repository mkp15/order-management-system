package com.trade.order_management;

import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.repository.OrderBook;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PriceCalculatorHelper {

    private OrderBook orderBook;
    public PriceCalculatorHelper(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public int calculatePrice(String symbol, Side side, int amount){
      // Need to address sorting, it should be sorted at insertion time itself
      // and adjust itself automatically as item gets added or removed;  sort of Priority Query
        Stream<Order> orderStream = orderBook.getOrdersBySymbol(symbol).stream()
              .filter(order -> order.side() == side);
        List<Order> sortedOrders = Side.BUY == side
                ? orderStream.toList()
                : orderStream.sorted(Comparator.comparing(Order::price).reversed()).toList();

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
