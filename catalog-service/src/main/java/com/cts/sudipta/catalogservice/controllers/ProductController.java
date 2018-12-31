package com.cts.sudipta.catalogservice.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sudipta.catalogservice.entities.Product;
import com.cts.sudipta.catalogservice.exception.ProductNotFoundException;
import com.cts.sudipta.catalogservice.services.ProductService;

import lombok.extern.slf4j.Slf4j;
 
@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
	@Autowired
	private Environment environment; 
 
    private final ProductService productService;
 
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
 
    @GetMapping("")
    public List<Product> allProducts() {
    	System.out.println("spring.datasource.url >>> " + environment.getProperty("spring.datasource.url"));
        return productService.findAllProducts();
    }
 
    @GetMapping("/{code}")
    public Product productByCode(@PathVariable String code) {
        return productService.findProductByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }   
}