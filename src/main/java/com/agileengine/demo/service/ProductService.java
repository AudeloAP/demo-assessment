package com.agileengine.demo.service;

import com.agileengine.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> getAllProducts();
    public Optional<Product> getProductById(Integer id);
    public Integer saveProduct(Product product);
    public void deleteProduct(Integer id);
}
