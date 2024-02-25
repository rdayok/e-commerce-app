package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Store;

import com.rdi.ecommerce.exceptions.StoreNotFoundException;

public interface StoreService {
    Store getStoreBy(Long storeId) throws StoreNotFoundException;
}
