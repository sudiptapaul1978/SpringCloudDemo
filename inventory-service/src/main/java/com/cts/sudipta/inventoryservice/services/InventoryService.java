package com.cts.sudipta.inventoryservice.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.sudipta.inventoryservice.entities.Inventory;
import com.cts.sudipta.inventoryservice.repositories.InventoryRepository;

import lombok.extern.slf4j.Slf4j;
 
@Service
@Transactional
@Slf4j
public class InventoryService {
	private final InventoryRepository inventoryRepository;

    

    @Autowired
    public InventoryService(InventoryRepository productRepository) {
        this.inventoryRepository = productRepository;
    }
 
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }
 
    public Optional<Inventory> findInventoryByProductCode(String code) {
        return inventoryRepository.findByProductCode(code);
    }
}