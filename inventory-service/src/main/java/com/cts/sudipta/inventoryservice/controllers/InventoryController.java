package com.cts.sudipta.inventoryservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sudipta.inventoryservice.entities.Inventory;
import com.cts.sudipta.inventoryservice.services.InventoryService;

import lombok.extern.slf4j.Slf4j;
 
@RestController
@Slf4j
public class InventoryController { 
	
	private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @GetMapping("/api/inventory/{productCode}")
    public ResponseEntity<Inventory> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
		System.out.println("Finding inventory for product code :" + productCode);
        Optional<Inventory> inventory = inventoryService.findInventoryByProductCode(productCode);
        if(inventory.isPresent()) {
            return new ResponseEntity(inventory, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/inventory")
    public List<Inventory> getInventory() {
    	System.out.println("Finding inventory for all products ");
        return inventoryService.findAll();
    }
}