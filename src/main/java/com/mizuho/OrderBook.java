package com.mizuho;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OrderBook {
    private Map<Long, Order> orderMap = new ConcurrentHashMap<>();

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
    }

    public void removeOrder(long orderId){
        orderMap.remove(orderId);
    }

    public void updateOrder(long orderId, long size) throws IllegalArgumentException{
        Order order = orderMap.get(orderId);
        if(order != null){
            order.setSize(size);
        }else{
            throw new IllegalArgumentException("Order Not Found");
        }
    }

    public double getBestPriceBySideAndLevel(char side, int level) throws IllegalArgumentException{
        List<Order> ordersBySide = getOrdersBySideSortedByLevel(side);
        if(ordersBySide.size() >= level)
            return getOrdersBySideSortedByLevel(side).get(level-1).getPrice();
        else
            throw new IllegalArgumentException("Invalid level specified.");
    }

    public long getTotalSizeBySideAndLevel(char side, int level) throws IllegalArgumentException{
        List<Order> ordersBySide = getOrdersBySideSortedByLevel(side);
        if(ordersBySide.size() >= level)
            return getOrdersBySideSortedByLevel(side).get(level-1).getSize();
        else
            throw new IllegalArgumentException("Invalid level specified.");
    }

    public List<Order> getOrdersBySideSortedByLevel(char side){
        List<Order> ordersBySide = getOrdersBySide(side);
        Collections.sort(ordersBySide, (o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
        return ordersBySide;
    }

    public Collection<Order> getAllOrders(){
        return orderMap.values();
    }

    public Order getOrderById(long orderId){
        return orderMap.get(orderId);
    }
    private List<Order> getOrdersBySide(char side){
        return orderMap.entrySet().parallelStream().filter(entry -> entry.getValue().getSide() == side)
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

}
