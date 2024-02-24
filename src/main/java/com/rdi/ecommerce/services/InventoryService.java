package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.ProductRecordRequest;
import com.rdi.ecommerce.dto.ProductRecordResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;

public interface InventoryService {
    ProductRecordResponse addProductRecord(ProductRecordRequest productRecordRequest) throws MerchantNotFoundException, ProductNotFoundException;
}
