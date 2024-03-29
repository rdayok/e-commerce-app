package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.ProductInventory;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRestockRequest;
import com.rdi.ecommerce.exceptions.OnlyMerchantThatOwnProductCanRestockException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

import java.util.List;

public interface InventoryService {

    ApiResponse<?> restockProduct(ProductRestockRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException, OnlyMerchantThatOwnProductCanRestockException;

    ApiResponse<?> reserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException;

    ApiResponse<?> returnReserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException;

    void saveAll(List<ProductInventory> productInventoryList);
}
