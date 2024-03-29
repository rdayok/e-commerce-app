package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.InventoryRepository;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.ProductRestockRequest;
import com.rdi.ecommerce.exceptions.OnlyMerchantThatOwnProductCanRestockException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductInventoryNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.InventoryService;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ECommerceInventoryService implements InventoryService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public ApiResponse<?> restockProduct(ProductRestockRequest productRecordRequest) throws
            MerchantNotFoundException, ProductNotFoundException, OnlyMerchantThatOwnProductCanRestockException
    {
        Long productId = productRecordRequest.getProductId();
        Product gottenProduct = productService.getProductBy(productId);
        Long merchantId = productRecordRequest.getMerchantId();
        validateMerchantThatWantsToRestock(merchantId, gottenProduct);
        ProductInventory productInventory = gottenProduct.getProductInventory();
        Integer quantityOfProductToRestock = productRecordRequest.getProductQuantity();
        productInventory.restockProduct(quantityOfProductToRestock);
        inventoryRepository.save(productInventory);
        return new ApiResponse<>("SUCCESSFUL");
    }

    private void validateMerchantThatWantsToRestock(Long merchantId, Product gottenProduct) throws
            MerchantNotFoundException, OnlyMerchantThatOwnProductCanRestockException
    {
        Merchant gottenMerchant = merchantService.getMerchantBy(merchantId);
        Long storeIdFromGottenMerchant = gottenMerchant.getStore().getId();
        Long storeIdFromGottenProduct = gottenProduct.getStore().getId();
        Long productId = gottenProduct.getId();
        boolean isProductStoreNotSameAsMerchantStore = !Objects.equals(storeIdFromGottenProduct, storeIdFromGottenMerchant);
        if(isProductStoreNotSameAsMerchantStore) throw new OnlyMerchantThatOwnProductCanRestockException(
                String.format("The product with id %d is not yours", productId)
        );
    }

    @Override
    public ApiResponse<?> reserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException
    {
        ProductInventory productInventory = getProductInventory(productInventoryId);
        productInventory.reserveOneProduct();
        inventoryRepository.save(productInventory);
        return new ApiResponse<>("SUCCESSFUL");
    }

    @Override
    public ApiResponse<?> returnReserveProductBy(Long productInventoryId) throws ProductInventoryNotFoundException
    {
        ProductInventory productInventory = getProductInventory(productInventoryId);
        productInventory.returnOneProduct();
        inventoryRepository.save(productInventory);
        return  new ApiResponse<>("SUCCESSFUL");
    }

    @Override
    public void saveAll(List<ProductInventory> productInventoryList) {
        inventoryRepository.saveAll(productInventoryList);
    }

    private ProductInventory getProductInventory(Long productInventoryId) throws ProductInventoryNotFoundException {
        ProductInventory productInventory = inventoryRepository.findById(productInventoryId).orElseThrow(() ->
                new ProductInventoryNotFoundException(
                        String.format("The product inventory with the id %d does not exist", productInventoryId)
                ));
        return productInventory;
    }

}
