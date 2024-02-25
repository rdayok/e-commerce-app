package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRestockRequest;
import com.rdi.ecommerce.dto.ProductInventoryResponse;
import com.rdi.ecommerce.exceptions.CannotRestockAnotherMerchantProduct;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

public interface InventoryService {

    // TODO -> I will make this service to update product inventory record
    ApiResponse<?> restockProduct(ProductRestockRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException, CannotRestockAnotherMerchantProduct;

    ApiResponse<?> reserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException;

    ApiResponse<?> returnReserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException;
}
