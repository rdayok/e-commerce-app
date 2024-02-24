package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;

@SpringBootTest
public class InventoryServiceTest {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProductRecordToStoreInventory() throws
            StoreNotFoundException, MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
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
        productRequest.setStoreId(merchantRegisterResponse.getStore().getId());
//        productRequest.setQuantity(5);
        ProductResponse productResponse = productService.addProduct(productRequest);

        ProductRecordRequest productRecordRequest = new ProductRecordRequest();
//        productRecordRequest.setId();
    }
}
