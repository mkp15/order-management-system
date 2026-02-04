package com.trade.order_management;


import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.repository.OrderBook;
import com.trade.order_management.service.OrderManager;
import com.trade.order_management.service.OrderManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderManagementImplTest {

    // Assumption Not covers the point if user request more that order in booking system, shall it return exception ?
    @Test
    @DisplayName("Test place order assuming user is happy with prices")
    public void placeOrder_buy_side(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(10, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(11, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(12, "AMZN", Side.SELL, 7, 10));
        orderBook.addOrder(new Order(13, "JPM", Side.BUY, 10, 21));
        PriceCalculatorHelper priceCalculator = new PriceCalculatorHelper(orderBook);
        OrderManager orderManager = new OrderManagerImpl(orderBook, priceCalculator);
        orderManager.placeOrder("JPM", Side.BUY, 22);
        orderBook.getOrdersBySymbol("JPM");
        Assertions.assertEquals(8, orderBook.getOrdersBySymbol("JPM").stream().findFirst().get().amount());
    }

    @Test
    @DisplayName("Test place order assuming user is happy with prices")
    public void placeOrder_sell_side(){
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(10, "JPM", Side.BUY, 20, 20));
        orderBook.addOrder(new Order(11, "GOOG", Side.SELL, 12, 25));
        orderBook.addOrder(new Order(12, "AMZN", Side.SELL, 7, 10));
        orderBook.addOrder(new Order(13, "JPM", Side.BUY, 10, 21));
        PriceCalculatorHelper priceCalculator = new PriceCalculatorHelper(orderBook);
        OrderManager orderManager = new OrderManagerImpl(orderBook, priceCalculator);
        orderManager.placeOrder("GOOG", Side.SELL, 12);
        Assertions.assertEquals(0, orderBook.getOrdersBySymbol("GOOG").stream().count());
    }

}
