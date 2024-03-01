package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/reserve/{productInventoryId}")
    public ResponseEntity<ApiResponse<?>> reserveProduct( @PathVariable Long productInventoryId) throws
            ProductInventoryNotFoundException {
        return ResponseEntity.status(OK).body(inventoryService.reserveProductBy(productInventoryId));
    }

    @PostMapping("/return/{productInventoryId}")
    public ResponseEntity<ApiResponse<?>> returnProduct( @PathVariable Long productInventoryId) throws
            ProductInventoryNotFoundException {
        return ResponseEntity.status(OK).body(inventoryService.returnReserveProductBy(productInventoryId));
    }
}
