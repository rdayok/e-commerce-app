package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static com.rdi.ecommerce.services.CloudServiceTest.getTestFile;
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
    private ProductRequest productRequest;

    @BeforeEach
    public void setUp() {
        productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setInitialQuantity(5);
        productRequest.setProductPicture(getTestFile());
        productRequest.setPricePerUnit(BigDecimal.valueOf(50000));
    }

    @Test
    public void testReserveProductByProductInventoryId() throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException,
            ProductInventoryNotFoundException, MediaUploadException
    {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("dayokr@gmail.com");
        assertThat(merchantRegisterResponse).isNotNull();
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();

        ProductInventoryResponse productInventoryResponse = productResponse.getProductInventory();
        Long productInventoryId = productInventoryResponse.getId();
        ApiResponse<?> response = inventoryService.reserveProductBy(productInventoryId);

        assertThat(response).isNotNull();
        assertEquals("SUCCESSFUL", response.getMessage());
    }



    @Test
    public void testReturnReserveProductByProductInventoryId() throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException,
            ProductInventoryNotFoundException, MediaUploadException
    {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("max_ret@yahoo.com");
        assertThat(merchantRegisterResponse).isNotNull();
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        ProductInventoryResponse productInventoryResponse = productResponse.getProductInventory();
        Long productInventoryId = productInventoryResponse.getId();
        ApiResponse<?> reserveResponse = inventoryService.reserveProductBy(productInventoryId);
        assertThat(reserveResponse).isNotNull();

        ApiResponse<?> returnReserveResponse = inventoryService.returnReserveProductBy(productInventoryId);

        assertThat(returnReserveResponse).isNotNull();
        assertEquals("SUCCESSFUL", returnReserveResponse.getMessage());
    }


    @Test
    public void testRestockingA_Product() throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException,
            ProductNotFoundException, OnlyMerchantThatOwnProductCanRestockException,
            MediaUploadException
    {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("maxret@yahoo.com");
        assertThat(merchantRegisterResponse).isNotNull();
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();

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

    private MerchantRegisterResponse getMerchantRegisterResponse(String mail) {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail(mail);
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        assertThat(merchantRegisterResponse).isNotNull();
        return merchantRegisterResponse;
    }
}
