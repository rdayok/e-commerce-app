package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRecordRequest;
import com.rdi.ecommerce.dto.ProductInventoryResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

public interface InventoryService {

    // TODO -> I will make this service to update product inventory record
    ProductInventoryResponse addProductRecord(ProductRecordRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException;

    ApiResponse<?> reserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException;
}
