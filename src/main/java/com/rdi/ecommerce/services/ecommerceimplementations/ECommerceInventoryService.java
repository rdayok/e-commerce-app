package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRecordRequest;
import com.rdi.ecommerce.dto.ProductInventoryResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.InventoryService;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.ProductService;
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
    public ProductInventoryResponse addProductRecord(ProductRecordRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException {
        Long merchantId = productRecordRequest.getMerchantId();
        Long productId = productRecordRequest.getProductId();
        Merchant gottenMerchant = merchantService.getMerchantBy(merchantId);
        Store merchantStore = gottenMerchant.getStore();
        Product gottenProduct = productService.getProductBy(productId);
        ProductInventory productInventory = new ProductInventory();
        int productQuantity = productRecordRequest.getProductQuantity();
        productInventory.setAvailableQuantity(productQuantity);
        ProductInventory savedProductRecord = inventoryRepository.save(productInventory);
        return modelMapper.map(savedProductRecord, ProductInventoryResponse.class);
    }

    @Override
    public ApiResponse<?> reserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException {
        ProductInventory productInventory = inventoryRepository.findById(productInventoryId).orElseThrow(() ->
                new ProductInventoryNotFoundException(
                        String.format("The product inventory with the id %d does not exist", productInventoryId)
                ));
        productInventory.reserveOneProduct();
        ProductInventory updatedProductInventory = inventoryRepository.save(productInventory);
        System.out.println(updatedProductInventory);
        return  new ApiResponse<>("SUCCESSFUL");
    }

}
