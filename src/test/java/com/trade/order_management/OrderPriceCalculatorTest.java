package com.trade.order_management;


import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.repository.OrderBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderPriceCalculatorTest {

    // Assumption Not covers the point if user request more that order in booking system, shall it return exception ?
    @Test
    @DisplayName("Best Price for BUY case")
    public void bestPriceCalculatorBuyCase(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(1, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(1, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(1, "AMZN", Side.SELL, 7, 10));
        orderBook.addOrder(new Order(1, "JPM", Side.BUY, 10, 21));
        PriceCalculatorHelper priceCalculator = new PriceCalculatorHelper(orderBook);
        Assertions.assertEquals(400.00, priceCalculator.calculatePrice("JPM", Side.BUY, 20));
        Assertions.assertEquals(200, priceCalculator.calculatePrice("JPM", Side.BUY, 10));
        Assertions.assertEquals(442.00, priceCalculator.calculatePrice("JPM", Side.BUY, 22));
    }

    @Test
    @DisplayName("Best Price for Sell case")
    public void bestPriceCalculatorSellCase(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(1, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(1, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(1, "GOOG", Side.SELL, 5, 20));
        orderBook.addOrder(new Order(1, "AMZN", Side.SELL, 7, 10));
        PriceCalculatorHelper priceCalculator = new PriceCalculatorHelper(orderBook);
        Assertions.assertEquals(125.0, priceCalculator.calculatePrice("GOOG", Side.SELL, 5));
        Assertions.assertEquals(340.0, priceCalculator.calculatePrice("GOOG", Side.SELL, 14));
    }
}
