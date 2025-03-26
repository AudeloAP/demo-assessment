package com.agileengine.demo.service;

import com.agileengine.demo.model.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderProductService {
    List<OrderProduct> getAllOrderProduct();
    Optional<OrderProduct> getOrderProductById(Integer id);
    Integer saveOrderProduct(OrderProduct orderProduct);
    void deleteOrderProductById(Integer id);
}
