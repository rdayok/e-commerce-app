package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class InventoryServiceTest {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testAddProductRecordToStoreInventory() throws
            StoreNotFoundException, MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, ProductNotFoundException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setInitialQuantity(40);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        ProductRecordRequest productRecordRequest = new ProductRecordRequest();
        productRecordRequest.setProductQuantity(50);
        productRecordRequest.setProductId(productResponse.getId());
        productRecordRequest.setMerchantId(merchantRegisterResponse.getId());

        ProductInventoryResponse productRecordResponse = inventoryService.addProductRecord(productRecordRequest);

        assertThat(productRecordResponse).isNotNull();
        log.info("{}", productRecordResponse);
    }

    @Test
    public void testReserveProductByProductInventoryId() throws
            StoreNotFoundException, MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, ProductNotFoundException, ProductInventoryNotFoundException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        Integer productInitialQuantity = 50;
        productRequest.setInitialQuantity(productInitialQuantity);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        ProductInventoryResponse productInventoryResponse = productResponse.getProductInventory();
        ApiResponse<?> response = inventoryService.reserveProductBy(productInventoryResponse.getId());
        assertThat(productInventoryResponse).isNotNull();
        assertThat(response).isNotNull();
        assertEquals("SUCCESSFUL", response.getMessage());
        log.info("{}", productInventoryResponse);
    }
}
