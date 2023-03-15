package com.mizuho;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    private OrderBook orderBook;

    @BeforeEach
    public void setUp(){
        orderBook = new OrderBook();
        createOrderBookWithOrders();
    }

    @Test
    void addOrder() {
        int noOfOrders = orderBook.getAllOrders().size();
        Order order = new Order(4, 563.45, 'B', 15000);
        orderBook.addOrder(order);
        assertEquals(noOfOrders+1, orderBook.getAllOrders().size(), "Failed to add Order.");
    }

    @Test
    void removeOrder() {
        int noOfOrders = orderBook.getAllOrders().size();
        Order order = new Order(1, 123.45, 'B', 10000);
        orderBook.removeOrder(1);
        assertEquals(noOfOrders-1, orderBook.getAllOrders().size(), "Failed to remove Order.");
    }

    @Test
    void updateOrder() {
        orderBook.updateOrder(1, 15000);
        Order order = orderBook.getOrderById(1);
        assertEquals(15000, order.getSize(), "Order Size failed to update.");
    }

    @Test
    void getBestPriceBySideAndInvalidLevel() throws IllegalArgumentException {

        assertThrows(IllegalArgumentException.class, () -> {
            orderBook.getBestPriceBySideAndLevel('B', 3);
        });
    }

    @Test
    void getBestPriceBySideAndValidLevel() throws IllegalArgumentException {
        double price = orderBook.getBestPriceBySideAndLevel('B', 1);
        assertEquals(123.45, price, "Price does not match.");
    }

    @Test
    void getTotalSizeBySideAndValidLevel() {
        long size = orderBook.getTotalSizeBySideAndLevel('B', 1);
        assertEquals(10000, size, "Total size is incorrect.");
    }

    @Test
    void getTotalSizeBySideAndInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderBook.getTotalSizeBySideAndLevel('B', 3);
        });
    }
    @Test
    void getOrdersBySideSordedByLevel() {
        List<Order> orders = orderBook.getOrdersBySideSortedByLevel('B');
        Order order1 = new Order(1, 123.45, 'B', 10000);
        assertEquals(order1, orders.get(0), "Orders are not fetched in corrected order.");

    }

    private OrderBook createOrderBookWithOrders(){
        Order order1 = new Order(1, 123.45, 'B', 10000);
        Order order2 = new Order(2, 456.78, 'S', 20000);
        Order order3 = new Order(3, 535.63, 'B', 6000);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);
        return orderBook;
    }
}