package com.agileengine.demo.controller;

import com.agileengine.demo.model.ErrorResponseBody;
import com.agileengine.demo.model.Product;
import com.agileengine.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>>  getAllProducts() {
        logger.info(" --- Running getAllProducts controller");
        List<Product> finalList = productService.getAllProducts();
        if (finalList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(finalList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        logger.info(" --- Running getProductById controller");
        Optional<Product> product = productService.getProductById(id);

        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody Product product){
        logger.info(" --- Running createProduct controller");
        Integer newProductId = productService.saveProduct(product);

        return new ResponseEntity<>(newProductId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Integer id, @RequestBody Product product){
        logger.info(" --- Running updateProductById controller");

        Optional<Product> productToUpdate = productService.getProductById(id);

        if (!productToUpdate.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//        if (product.getId() != null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!product.getName().isBlank() && !product.getDescription().isBlank()) {
            Product finalProduct = productToUpdate.get();
            finalProduct.setDescription(product.getDescription());
            finalProduct.setName(product.getName());

            productService.saveProduct(finalProduct);
            return new ResponseEntity<>(finalProduct, HttpStatus.OK);
        } else if ( !product.getName().isBlank()) {
            Product finalProduct = productToUpdate.get();
            finalProduct.setName(product.getName());

            productService.saveProduct(finalProduct);
            return new ResponseEntity<>(finalProduct, HttpStatus.OK);
        } else if (!product.getDescription().isBlank()) {
            Product finalProduct = productToUpdate.get();
            finalProduct.setDescription(product.getDescription());

            productService.saveProduct(finalProduct);
            return new ResponseEntity<>(finalProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteProductById(@PathVariable Integer id){
        logger.info(" --- Running deleteProductById controller");

        Optional<Product> productToDelete = productService.getProductById(id);

        if (!productToDelete.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productService.deleteProduct(id);
        return new ResponseEntity<>(productToDelete.get().getId(), HttpStatus.OK);
    }

}
