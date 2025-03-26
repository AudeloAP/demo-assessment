package com.agileengine.demo.service;
import com.agileengine.demo.model.Order;
import com.agileengine.demo.repository.OrderRepository;
import com.agileengine.demo.service.impl.OrdersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setDescription("Test Order");
    }

    @Test
    void testGetAllOrders_Success() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = ordersService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(order.getDescription(), result.get(0).getDescription());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrders_EmptyList() {
        when(orderRepository.findAll()).thenReturn(List.of());

        List<Order> result = ordersService.getAllOrders();

        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testSaveOrder_Success() {
        when(orderRepository.save(order)).thenReturn(order);

        Integer orderId = ordersService.saveOrder(order);

        assertNotNull(orderId);
        assertEquals(order.getId(), orderId);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Optional<Order> result = ordersService.getOrderById(1);

        assertTrue(result.isPresent());
        assertEquals(order.getDescription(), result.get().getDescription());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Order> result = ordersService.getOrderById(1);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteOrderById_Success() {
        doNothing().when(orderRepository).deleteById(1);

        assertDoesNotThrow(() -> ordersService.deleteOrderById(1));

        verify(orderRepository, times(1)).deleteById(1);
    }
}
