package com.agileengine.demo.controller;

import com.agileengine.demo.model.Order;
import com.agileengine.demo.model.OrderItemRequest;
import com.agileengine.demo.model.OrderProduct;
import com.agileengine.demo.model.Product;
import com.agileengine.demo.service.OrderProductService;
import com.agileengine.demo.service.OrdersService;
import com.agileengine.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderProductController {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductController.class);

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<OrderProduct>> getAllOrderItems(){
        logger.info(" --- Running getAllOrderItems controller.");

        List<OrderProduct> listOrderProducts = orderProductService.getAllOrderProduct();
        if (listOrderProducts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(listOrderProducts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getOrderItemById(@PathVariable Integer id){
        logger.info(" --- Running getOrderItemById controller.");

        Optional<OrderProduct> orderItem = orderProductService.getOrderProductById(id);
        return orderItem.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Integer> createOrderItem(@RequestBody OrderProduct orderProduct){
        logger.info(" --- Running createOrderItem controller.");
        Integer orderItemId = orderProductService.saveOrderProduct(orderProduct);

        return new ResponseEntity<>(orderItemId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderProduct> updateOrtderItem(@PathVariable Integer id, @RequestBody OrderItemRequest orderProduct){
        logger.info(" --- Running updateOrtderItem controller");
        Optional<OrderProduct> orderItem = orderProductService.getOrderProductById(id);
        Integer orderId = orderProduct.getOrder_id();
        Integer productId = orderProduct.getProduct_id();

        if (orderItem.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(orderId.equals(0) || productId.equals(0))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Product> productFounded = productService.getProductById(productId);
        Optional<Order> orderFounded =  ordersService.getOrderById(orderId);
        if (productFounded.isEmpty() || orderFounded.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderProduct orderItemToSave = new OrderProduct();
        orderItemToSave.setProduct(productFounded.get());
        orderItemToSave.setOrder(orderFounded.get());

        orderProductService.saveOrderProduct(orderItemToSave);
        return new ResponseEntity<>(orderItemToSave, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteOrderItem(@PathVariable Integer id){
        logger.info(" --- Running deleteOrderItem controller.");

        Optional<OrderProduct> orderItem = orderProductService.getOrderProductById(id);
        if (orderItem.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        orderProductService.deleteOrderProductById(id);
        return new ResponseEntity<>(orderItem.get().getId(), HttpStatus.OK);
    }
}
