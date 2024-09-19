package com.mg.product_microservice.controller;

import com.mg.product_microservice.entity.Product;
import com.mg.product_microservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createProduct(@RequestBody Product product){
        productRepository.save(product);
    }
}
