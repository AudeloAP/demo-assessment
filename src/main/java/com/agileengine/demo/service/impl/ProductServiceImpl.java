package com.agileengine.demo.service.impl;

import com.agileengine.demo.model.Product;
import com.agileengine.demo.repository.ProductRepository;
import com.agileengine.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        logger.info(" --- Running getAllProducts service");
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Integer saveProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }


}
