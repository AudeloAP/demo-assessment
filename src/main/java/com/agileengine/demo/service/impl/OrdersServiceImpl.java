package com.agileengine.demo.service.impl;

import com.agileengine.demo.model.Order;
import com.agileengine.demo.repository.OrderRepository;
import com.agileengine.demo.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {
    private static final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        logger.info(" --- Running getAllOrders service");
        return orderRepository.findAll();
    }

    @Override
    public Integer saveOrder(Order order) {
        logger.info(" --- Running saveOrder service");
        return orderRepository.save(order).getId();
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        logger.info(" --- Running getOrderById service");
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrderById(Integer id) {
        logger.info(" --- Running deleteOrderById service");
        orderRepository.deleteById(id);
    }
}
