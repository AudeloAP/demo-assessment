package com.agileengine.demo.controller;

import com.agileengine.demo.model.Product;
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
@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setDescription("Test Description");

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(product.getName()));
    }

    @Test
    void testGetAllProducts_NotFound() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductById_Success() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProductById_Success() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.of(product));
        when(productService.saveProduct(product)).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()));
    }

    @Test
    void testUpdateProductById_NotFound() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProductById_Success() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.of(product));
        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProductById_NotFound() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound());
    }
}
