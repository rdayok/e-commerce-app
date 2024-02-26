package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class OrderServiceTest {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderService orderService;


    @Test
    public void testCheck() throws
            StoreNotFoundException, MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, BuyerNotFoundException,
            ProductNotFoundException, ProductInventoryNotFoundException {

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
        productRequest.setPricePerUnit(BigDecimal.valueOf(200_000));
        productRequest.setInitialQuantity(productInitialQuantity);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);

        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
        userRegisterRequestForBuyer.setEmail("max_ret@yahoo.com");
        userRegisterRequestForBuyer.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setBuyerId(buyerRegisterResponse.getId());
        CartResponse cartResponse = cartService.createCart(cartRequest);

        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProductId(productResponse.getId());
        addToCartRequest.setBuyerId(buyerRegisterResponse.getId());
        ApiResponse<?> response = cartItemService.addCartItem(addToCartRequest);
        assertThat(response).isNotNull();
        ApiResponse<?> response2 = cartItemService.addCartItem(addToCartRequest);
        assertThat(response2).isNotNull();

        CheckOutResponse checkOutResponse = orderService.checkOut(buyerRegisterResponse.getId());

        assertThat(checkOutResponse).isNotNull();
        log.info("{}", checkOutResponse);
    }
}
