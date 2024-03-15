package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRestockRequest;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.OnlyMerchantThatOwnProductCanRestockException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/restock")
    public ResponseEntity<ApiResponse<?>> restockProduct(@Valid @RequestBody ProductRestockRequest productRestockRequest)
            throws OnlyMerchantThatOwnProductCanRestockException, MerchantNotFoundException, ProductNotFoundException {
        return ResponseEntity.status(OK).body(inventoryService.restockProduct(productRestockRequest));
    }
}
