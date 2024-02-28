package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.dto.GetAllProductRequest;
import com.rdi.ecommerce.dto.GetAllProductsResponse;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest) throws
            StoreNotFoundException, MerchantNotFoundException, MerchantIsNotOwnerOfStoreException;

    Product getProductBy(Long productId) throws ProductNotFoundException;

    List<GetAllProductsResponse> getAllProducts(GetAllProductRequest getAllProductRequest);
}
