package com.agileengine.demo.service.impl;

import com.agileengine.demo.model.Product;
import com.agileengine.demo.model.ProductDTO;
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
        logger.info(" --- Running getProductById service");

        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        logger.info(" --- Running saveProduct service");
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        logger.info(" --- Running deleteProduct service");

        productRepository.deleteById(id);
    }

    @Override
    public Product saveProductById(ProductDTO product, Integer id) {
        logger.info(" --- Running saveProductById service");
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setId(id);
        return productRepository.save(newProduct);
    }

}
