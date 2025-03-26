package com.agileengine.demo.service;

import com.agileengine.demo.model.Product;
import com.agileengine.demo.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> getAllProducts();
    public Optional<Product> getProductById(Integer id);
    public Product saveProduct(Product product);
    public void deleteProduct(Integer id);
    public Product saveProductById(ProductDTO product, Integer id);
}
