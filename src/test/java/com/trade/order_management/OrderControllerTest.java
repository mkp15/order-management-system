package com.trade.order_management;

import com.trade.order_management.domain.Order;
import com.trade.order_management.enums.Side;
import com.trade.order_management.rest.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class OrderControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void testOrderBooking() {
        restTestClient.post()
                .uri("http://localhost:%d/order".formatted(port))
                .body(new Order(1, "JPM", Side.BUY, 10, 20))
                .exchange()
                .expectBody(String.class)
                .isEqualTo("Order got added successfully to order booking");
    }

    @Test
    void testOrderDeletion() {
        restTestClient.post()
                .uri("http://localhost:%d/order/delete".formatted(port))
                .body(1)
                .exchange()
                .expectBody(String.class)
                .isEqualTo("Order ID: 1 deleted successfully");
    }

    @Test
    void testPlaceOrder() {
        restTestClient.post()
                .uri("http://localhost:%d/order/place".formatted(port))
                .body(new Order(1, "JPM", Side.BUY, 10, 20))
                .exchange()
                .expectBody(OrderResponse.class)
                .isEqualTo(new OrderResponse<>("Order placed successfully"));
    }

    @Test
    void testCalculateOrder() {
        testOrderDeletion();
        restTestClient.post()
                .uri("http://localhost:%d/order/price".formatted(port))
                .body(new Order(1, "JPM", Side.BUY, 10, 20))
                .exchange()
                .expectBody(OrderResponse.class)
                .isEqualTo(new OrderResponse<>(0));
    }


}
