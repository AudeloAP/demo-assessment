package com.agileengine.demo.service;

import com.agileengine.demo.model.Product;
import com.agileengine.demo.model.ProductDTO;
import com.agileengine.demo.repository.ProductRepository;
import com.agileengine.demo.service.impl.ProductServiceImpl;
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
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setDescription("Test Description");
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_EmptyList() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<Product> result = productService.getAllProducts();

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1);

        assertTrue(result.isPresent());
        assertEquals(product.getName(), result.get().getName());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(1);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testSaveProduct_Success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        doNothing().when(productRepository).deleteById(1);

        assertDoesNotThrow(() -> productService.deleteProduct(1));

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testSaveProductById_Success() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Name");
        productDTO.setDescription("Updated Description");

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setDescription(productDTO.getDescription());

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.saveProductById(productDTO, 1);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getDescription(), result.getDescription());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
