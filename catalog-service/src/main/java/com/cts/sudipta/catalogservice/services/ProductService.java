package com.cts.sudipta.catalogservice.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cts.sudipta.catalogservice.entities.Product;
import com.cts.sudipta.catalogservice.model.ProductInventoryResponse;
import com.cts.sudipta.catalogservice.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;
 
@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
 
    @Autowired
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }
 
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
 
    /*public Optional<Product> findProductByCode(String code) {
        return productRepository.findByCode(code);
    }*/
    
    public Optional<Product> findProductByCode(String code) {
    	Optional<Product> productOptional = productRepository.findByCode(code);
        if(productOptional.isPresent()) {
            System.out.println("Fetching inventory level for product_code: "+code);
            ResponseEntity<ProductInventoryResponse> itemResponseEntity =
                    restTemplate.getForEntity("http://inventory-service/api/inventory/{code}",
                                                ProductInventoryResponse.class,
                                                code);
            if(itemResponseEntity.getStatusCode() == HttpStatus.OK) {
                Integer quantity = itemResponseEntity.getBody().getAvailableQuantity();
                System.out.println("Available quantity: "+quantity);
                productOptional.get().setInStock(quantity> 0);
            } else {
            	System.out.println("Unable to get inventory level for product_code: "+code +
                ", StatusCode: "+itemResponseEntity.getStatusCode());
            }
        }
        return productOptional;
    }
}