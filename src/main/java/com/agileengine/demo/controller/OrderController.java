package com.agileengine.demo.controller;

import com.agileengine.demo.model.Order;
import com.agileengine.demo.model.OrderStatus;
import com.agileengine.demo.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        logger.info(" --- Running getAllOrders controller");

        List<Order> ordersList = ordersService.getAllOrders();
        if(ordersList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.info(" ---###--- ordersList: {}", ordersList.toString());
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody Order order){
        logger.info(" --- Running createOrder controller");

        Integer newOrder = ordersService.saveOrder(order);

        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id){
        logger.info(" --- Running getOrderById controller");

        Optional<Order> order = ordersService.getOrderById(id);

        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order){
        logger.info(" --- Running updateOrder controller");

        Optional<Order> orderToUpdate = ordersService.getOrderById(id);

        if (orderToUpdate.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (order.getStatus().describeConstable().isEmpty()){
            logger.info(" Creating new order");
            Order orderToSave = new Order();
            orderToSave.setStatus(OrderStatus.PENDING.toString());
            orderToSave.setDescription(order.getDescription());

            ordersService.saveOrder(orderToSave);
            return new ResponseEntity<>(orderToSave, HttpStatus.OK);
        }
        if (order.getStatus().equals(OrderStatus.CANCELLED.toString())
                && orderToUpdate.get().getStatus().equals(OrderStatus.PENDING.toString())) {
            logger.info( " Canceling an order");
            Order newOrder = orderToUpdate.get();
            newOrder.setStatus(OrderStatus.CANCELLED.toString());

            ordersService.saveOrder(newOrder);
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        }
        if (order.getStatus().equals(OrderStatus.COMPLETED.toString())
                && orderToUpdate.get().getStatus().equals(OrderStatus.PENDING.toString())) {
            logger.info( " Completing an order");
            Order newOrder = orderToUpdate.get();
            newOrder.setStatus(OrderStatus.COMPLETED.toString());

            ordersService.saveOrder(newOrder);
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteOrderById(@PathVariable Integer id){
        logger.info(" --- Running deleteOrderById controller.");

        Optional<Order> orderToDelete = ordersService.getOrderById(id);

        if (!orderToDelete.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ordersService.deleteOrderById(id);

        return new ResponseEntity<>(orderToDelete.get().getId(), HttpStatus.OK);
    }

}
