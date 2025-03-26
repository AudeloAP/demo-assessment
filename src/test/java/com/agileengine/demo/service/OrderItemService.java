package com.agileengine.demo.service;
import com.agileengine.demo.model.OrderProduct;
import com.agileengine.demo.repository.OrderProductRepository;
import com.agileengine.demo.service.impl.OrderProductServiceImpl;
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
public class OrderItemService {

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderProductServiceImpl orderProductService;

    private OrderProduct orderProduct;

    @BeforeEach
    void setUp() {
        orderProduct = new OrderProduct();
        orderProduct.setId(1);
    }

    @Test
    void testGetAllOrderProduct_Success() {
        List<OrderProduct> orderProducts = Arrays.asList(orderProduct);
        when(orderProductRepository.findAll()).thenReturn(orderProducts);

        List<OrderProduct> result = orderProductService.getAllOrderProduct();

        assertEquals(1, result.size());
        assertEquals(orderProduct.getId(), result.get(0).getId());
        verify(orderProductRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrderProduct_EmptyList() {
        when(orderProductRepository.findAll()).thenReturn(List.of());

        List<OrderProduct> result = orderProductService.getAllOrderProduct();

        assertTrue(result.isEmpty());
        verify(orderProductRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderProductById_Success() {
        when(orderProductRepository.findById(1)).thenReturn(Optional.of(orderProduct));

        Optional<OrderProduct> result = orderProductService.getOrderProductById(1);

        assertTrue(result.isPresent());
        assertEquals(orderProduct.getId(), result.get().getId());
        verify(orderProductRepository, times(1)).findById(1);
    }

    @Test
    void testGetOrderProductById_NotFound() {
        when(orderProductRepository.findById(1)).thenReturn(Optional.empty());

        Optional<OrderProduct> result = orderProductService.getOrderProductById(1);

        assertFalse(result.isPresent());
        verify(orderProductRepository, times(1)).findById(1);
    }

    @Test
    void testSaveOrderProduct_Success() {
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct);

        Integer savedOrderProductId = orderProductService.saveOrderProduct(orderProduct);

        assertNotNull(savedOrderProductId);
        assertEquals(orderProduct.getId(), savedOrderProductId);
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    }

    @Test
    void testDeleteOrderProductById_Success() {
        doNothing().when(orderProductRepository).deleteById(1);

        assertDoesNotThrow(() -> orderProductService.deleteOrderProductById(1));

        verify(orderProductRepository, times(1)).deleteById(1);
    }
}
