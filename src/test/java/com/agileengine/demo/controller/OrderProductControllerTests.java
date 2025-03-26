package com.agileengine.demo.controller;
import com.agileengine.demo.model.Order;
import com.agileengine.demo.model.OrderItemRequest;
import com.agileengine.demo.model.OrderProduct;
import com.agileengine.demo.model.Product;
import com.agileengine.demo.service.OrderProductService;
import com.agileengine.demo.service.OrdersService;
import com.agileengine.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderProductController.class)
public class OrderProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderProductService orderProductService;

    @MockBean
    private OrdersService ordersService;

    @MockBean
    private ProductService productService;

    private OrderProduct orderProduct;
    private OrderItemRequest orderItemRequest;
    private Product product;
    private Order order;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Test Product");

        order = new Order();
        order.setId(1);
        order.setDescription("Test Order");

        orderProduct = new OrderProduct();
        orderProduct.setId(1);
        orderProduct.setProduct(product);
        orderProduct.setOrder(order);

        orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProduct_id(1);
        orderItemRequest.setOrder_id(1);

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllOrderItems_Success() throws Exception {
        when(orderProductService.getAllOrderProduct()).thenReturn(Collections.singletonList(orderProduct));

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].product.id").value(product.getId()))
                .andExpect(jsonPath("$[0].order.id").value(order.getId()));
    }

    @Test
    void testGetAllOrderItems_NotFound() throws Exception {
        when(orderProductService.getAllOrderProduct()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOrderItemById_Success() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.of(orderProduct));

        mockMvc.perform(get("/api/order-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.id").value(product.getId()))
                .andExpect(jsonPath("$.order.id").value(order.getId()));
    }

    @Test
    void testGetOrderItemById_NotFound() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/order-items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrderItem_Success() throws Exception {
        when(orderProductService.saveOrderProduct(orderProduct)).thenReturn(1);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderProduct)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateOrderItem_Success() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.of(orderProduct));
        when(productService.getProductById(1)).thenReturn(Optional.of(product));
        when(ordersService.getOrderById(1)).thenReturn(Optional.of(order));
        when(orderProductService.saveOrderProduct(orderProduct)).thenReturn(1);

        mockMvc.perform(put("/api/order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.id").value(product.getId()))
                .andExpect(jsonPath("$.order.id").value(order.getId()));
    }

    @Test
    void testUpdateOrderItem_NotFound() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemRequest)))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteOrderItem_Success() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.of(orderProduct));
        doNothing().when(orderProductService).deleteOrderProductById(1);

        mockMvc.perform(delete("/api/order-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(orderProduct.getId()));
    }

    @Test
    void testDeleteOrderItem_NotFound() throws Exception {
        when(orderProductService.getOrderProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/order-items/1"))
                .andExpect(status().isNotFound());
    }
}
