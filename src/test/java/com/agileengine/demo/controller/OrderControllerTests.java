package com.agileengine.demo.controller;
import com.agileengine.demo.model.Order;
import com.agileengine.demo.model.OrderStatus;
import com.agileengine.demo.service.OrdersService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrdersService ordersService;

	@Autowired
	private ObjectMapper objectMapper;

	private Order order;

	@BeforeEach
	void setUp() {
		order = new Order();
		order.setId(1);
		order.setDescription("Test Order");
		order.setStatus(OrderStatus.PENDING.toString());
	}

	@Test
	void getAllOrders_ShouldReturnListOfOrders() throws Exception {
		when(ordersService.getAllOrders()).thenReturn(List.of(order));

		mockMvc.perform(get("/api/orders"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1))
				.andExpect(jsonPath("$[0].description").value(order.getDescription()));

		verify(ordersService, times(1)).getAllOrders();
	}

	@Test
	void getAllOrders_ShouldReturnNotFoundWhenEmpty() throws Exception {
		when(ordersService.getAllOrders()).thenReturn(List.of());

		mockMvc.perform(get("/api/orders"))
				.andExpect(status().isNotFound());

		verify(ordersService, times(1)).getAllOrders();
	}

	@Test
	void createOrder_ShouldReturnCreatedStatus() throws Exception {
		when(ordersService.saveOrder(any(Order.class))).thenReturn(order.getId());

		mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(order)))
				.andExpect(status().isCreated())
				.andExpect(content().string(order.getId().toString()));

		verify(ordersService, times(1)).saveOrder(any(Order.class));
	}

	@Test
	void getOrderById_ShouldReturnOrderWhenFound() throws Exception {
		when(ordersService.getOrderById(order.getId())).thenReturn(Optional.of(order));

		mockMvc.perform(get("/api/orders/{id}", order.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.description").value(order.getDescription()));

		verify(ordersService, times(1)).getOrderById(order.getId());
	}

	@Test
	void getOrderById_ShouldReturnNotFoundWhenNotExists() throws Exception {
		when(ordersService.getOrderById(anyInt())).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/orders/{id}", 99))
				.andExpect(status().isNotFound());

		verify(ordersService, times(1)).getOrderById(99);
	}

	@Test
	void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
		Order updatedOrder = new Order();
		updatedOrder.setId(order.getId());
		updatedOrder.setDescription("Updated Order");
		updatedOrder.setStatus(OrderStatus.COMPLETED.toString());

		when(ordersService.getOrderById(order.getId())).thenReturn(Optional.of(order));
		when(ordersService.saveOrder(any(Order.class))).thenReturn(order.getId());

		mockMvc.perform(put("/api/orders/{id}", order.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedOrder)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(OrderStatus.COMPLETED.toString()));

		verify(ordersService, times(1)).getOrderById(order.getId());
		verify(ordersService, times(1)).saveOrder(any(Order.class));
	}

	@Test
	void updateOrder_ShouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
		when(ordersService.getOrderById(anyInt())).thenReturn(Optional.empty());

		mockMvc.perform(put("/api/orders/{id}", 99)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(order)))
				.andExpect(status().isNotFound());

		verify(ordersService, times(1)).getOrderById(99);
	}

	@Test
	void deleteOrderById_ShouldReturnOkWhenDeleted() throws Exception {
		when(ordersService.getOrderById(order.getId())).thenReturn(Optional.of(order));
		doNothing().when(ordersService).deleteOrderById(order.getId());

		mockMvc.perform(delete("/api/orders/{id}", order.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(order.getId().toString()));

		verify(ordersService, times(1)).getOrderById(order.getId());
		verify(ordersService, times(1)).deleteOrderById(order.getId());
	}

	@Test
	void deleteOrderById_ShouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
		when(ordersService.getOrderById(anyInt())).thenReturn(Optional.empty());

		mockMvc.perform(delete("/api/orders/{id}", 99))
				.andExpect(status().isNotFound());

		verify(ordersService, times(1)).getOrderById(99);
	}
}
