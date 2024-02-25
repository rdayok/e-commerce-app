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
        Long productInventoryId = productInventoryResponse.getId();
        ApiResponse<?> response = inventoryService.reserveProductBy(productInventoryId);

        assertThat(productInventoryResponse).isNotNull();
        assertThat(response).isNotNull();
        assertEquals("SUCCESSFUL", response.getMessage());
        log.info("{}", productInventoryResponse);
    }



    @Test
    public void testReturnReserveProductByProductInventoryId() throws
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
        Long productInventoryId = productInventoryResponse.getId();
        inventoryService.reserveProductBy(productInventoryId);
        ApiResponse<?> response = inventoryService.returnReserveProductBy(productInventoryId);

        assertThat(productInventoryResponse).isNotNull();
        assertThat(response).isNotNull();
        assertEquals("SUCCESSFUL", response.getMessage());
        log.info("{}", productInventoryResponse);
    }


    @Test
    public void testRestockingA_Product() throws
            StoreNotFoundException, MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, ProductNotFoundException, CannotRestockAnotherMerchantProduct {

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

        ProductRestockRequest productRestockRequest = new ProductRestockRequest();
        Long productId = productResponse.getId();
        productRestockRequest.setProductId(productId);
        Long merchantId = merchantRegisterResponse.getId();
        productRestockRequest.setMerchantId(merchantId);
        Integer quantityOfProduct = 100;
        productRestockRequest.setProductQuantity(quantityOfProduct);

        ApiResponse<?> productRestockResponse = inventoryService.restockProduct(productRestockRequest);
        assertThat(productRestockResponse).isNotNull();
        assertEquals("SUCCESSFUL", productRestockResponse.getMessage());
    }
}
