package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.dto.ProductRecordRequest;
import com.rdi.ecommerce.dto.ProductRecordResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.InventoryService;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceInventoryService implements InventoryService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProductRecordResponse addProductRecord(ProductRecordRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException {
        Long merchantId = productRecordRequest.getMerchantId();
        Long productId = productRecordRequest.getProductId();
        Merchant gottenMerchant = merchantService.getMerchantBy(merchantId);
        Store merchantStore = gottenMerchant.getStore();
        Product gottenProduct = productService.getProductBy(productId);
        ProductRecord productRecord = new ProductRecord();
        productRecord.setProduct(gottenProduct);
        productRecord.setStore(merchantStore);
        int productQuantity = productRecordRequest.getProductQuantity();
        productRecord.setAvailableQuantity(productQuantity);
        ProductRecord savedProductRecord = inventoryRepository.save(productRecord);
        return modelMapper.map(savedProductRecord, ProductRecordResponse.class);
    }

}
