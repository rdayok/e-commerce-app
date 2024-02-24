package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class CartServiceTest {
//
//    @Autowired
//    private MerchantService merchantService;
//    @Autowired
//    private BuyerService buyerService;
//    @Autowired
//    private StoreService storeService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private CartService cartService;
//
//    @Test
//    public void testCreateCart() throws BuyerNotFoundException {
//        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
//        userRegisterRequestForBuyer.setEmail("dayokr@gmail.com");
//        userRegisterRequestForBuyer.setPassword("secretekey");
//        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
//        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
//        buyerRegisterRequest.setPhoneNumber("07031005737");
//        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);
//
//        CartRequest cartRequest = new CartRequest();
//        cartRequest.setBuyerId(buyerRegisterResponse.getId());
//        CartResponse cartResponse = cartService.createCart(cartRequest);
//
//        assertThat(cartResponse).isNotNull();
//        log.info("{}", cartResponse);
//    }
//
//    @Test
//    public void testCreatingCartASecondTimeForA_Buyer_returnsTheExistingCartId() throws BuyerNotFoundException {
//        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
//        userRegisterRequestForBuyer.setEmail("dayokr@gmail.com");
//        userRegisterRequestForBuyer.setPassword("secretekey");
//        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
//        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
//        buyerRegisterRequest.setPhoneNumber("07031005737");
//        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);
//        CartRequest cartRequest = new CartRequest();
//        cartRequest.setBuyerId(buyerRegisterResponse.getId());
//        CartResponse cartResponse = cartService.createCart(cartRequest);
//
//        CartRequest cartRequest1 = new CartRequest();
//        cartRequest1.setBuyerId(buyerRegisterResponse.getId());
//        CartResponse cartResponse1 = cartService.createCart(cartRequest1);
//
//        assertThat(cartResponse1).isNotNull();
//        assertEquals(cartResponse.getCartId(), cartResponse1.getCartId());
//        log.info("{}", cartResponse);
//        log.info("{}", cartResponse1);
//    }


//
//    @Test
//    public void testAddingProductToCart() throws MerchantNotFoundException, StoreNotFoundException, BuyerNotFoundException, ProductNotFoundException {
//        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
//        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
//        userRegisterRequestForMerchant.setPassword("secretekey");
//        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
//        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
//        merchantRegisterRequest.setStoreName("wadrobe");
//        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
//        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
//        userRegisterRequestForBuyer.setEmail("max_ret@yahoo.com");
//        userRegisterRequestForBuyer.setPassword("secretekey");
//        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
//        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
//        buyerRegisterRequest.setPhoneNumber("07031005737");
//        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);
//        StoreRequest storeRequest = new StoreRequest();
//        storeRequest.setStoreName("Wardrobe");
//        storeRequest.setMerchantId(merchantRegisterResponse.getId());
//        StoreResponse storeResponse = storeService.createStore(storeRequest);
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setProductName("TV");
//        productRequest.setProductCategory(ELECTRONIC);
//        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
//        productRequest.setMerchantId(merchantRegisterResponse.getId());
//        productRequest.setQuantity(5);
//        ProductResponse productResponse = productService.addProduct(productRequest);
//
//        AddToCartRequest addToCartRequest = new AddToCartRequest();
//        addToCartRequest.setProductId(productResponse.getId());
//        addToCartRequest.setBuyerId(buyerRegisterResponse.getId());
//        addToCartRequest.setQuantityOfProduct(3);
//        ApiResponse<?> response = cartService.addToCart(addToCartRequest);
//
//        assertThat(response).isNotNull();
//        log.info("{}", response);
//    }
}
