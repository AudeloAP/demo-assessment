package com.agileengine.demo.service;

import com.agileengine.demo.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    public List<Order> getAllOrders();
    public Integer saveOrder(Order order);
    public Optional<Order> getOrderById(Integer id);
    public void deleteOrderById(Integer id);
}
