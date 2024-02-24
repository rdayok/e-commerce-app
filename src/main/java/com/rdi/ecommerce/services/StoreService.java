package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.dto.StoreRequest;
import com.rdi.ecommerce.dto.StoreResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;

public interface StoreService {
    Store getStoreBy(Long storeId) throws StoreNotFoundException;
//    StoreResponse createStore(StoreRequest storeRequest) throws MerchantNotFoundException;

//    Store getStoreByMerchantId(Long merchantId) throws StoreNotFoundException;
}
