package com.trade.order_management;


import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.repository.OrderBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderBookTest {

    // Assumption Not covers the point if user request more that order in booking system, shall it return exception ?
    @Test
    @DisplayName("Test to check order being added to OrderBook")
    public void addOrder(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(10, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(11, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(12, "GOOG", Side.SELL, 5, 20));
        orderBook.addOrder(new Order(13, "AMZN", Side.SELL, 7, 10));
        orderBook.addOrder(new Order(14, "JPM", Side.BUY, 10, 22));
        Assertions.assertEquals(2, orderBook.getOrdersBySymbol("JPM").size());
        Assertions.assertEquals(2, orderBook.getOrdersBySymbol("GOOG").size());
        Assertions.assertEquals(1, orderBook.getOrdersBySymbol("AMZN").size());
        Assertions.assertEquals(5, orderBook.totalOrder());
    }

    @Test
    @DisplayName("Test to check order being removed from OrderBook")
    public void bestPriceCalculatorSellCase(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(10, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(11, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(12, "GOOG", Side.SELL, 5, 20));
        orderBook.addOrder(new Order(13, "AMZN", Side.SELL, 7, 10));
        orderBook.addOrder(new Order(14, "JPM", Side.BUY, 10, 22));
        orderBook.removeOrder(13);
        Assertions.assertEquals(4, orderBook.totalOrder());
    }
}
