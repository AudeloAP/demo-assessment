package com.agileengine.demo.service.impl;

import com.agileengine.demo.model.OrderProduct;
import com.agileengine.demo.repository.OrderProductRepository;
import com.agileengine.demo.service.OrderProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductServiceImpl.class);

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProduct> getAllOrderProduct() {
        logger.info(" --- Running getAllOrderProduct service.");
        return orderProductRepository.findAll();
    }

    @Override
    public Optional<OrderProduct> getOrderProductById(Integer id) {
        logger.info(" --- Running getOrderProductById service.");
        return orderProductRepository.findById(id);
    }

    @Override
    public Integer saveOrderProduct(OrderProduct orderProduct) {
        logger.info(" --- Running saveOrderProduct service.");
        return orderProductRepository.save(orderProduct).getId();
    }

    @Override
    public void deleteOrderProductById(Integer id) {
        logger.info(" --- Running deleteOrderProductById service.");
        orderProductRepository.deleteById(id);
    }
}
