package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class CartItemServiceTest {

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartItemService cartItemService;

    @Test
    public void testAddCartItem() throws
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
        log.info("{}", response);
    }

    @Test
    public void testRemoveCartItem() throws
            StoreNotFoundException, MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, BuyerNotFoundException,
            ProductNotFoundException, ProductInventoryNotFoundException, CannotTakeOutCartItemThatDoesNotExistInYOurCartException {

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
        cartItemService.addCartItem(addToCartRequest);
        ApiResponse<?> response = cartItemService.removeCartItem(addToCartRequest);

        assertThat(response).isNotNull();
        log.info("{}", response);
    }

    @Test
    public void testFindAllCartItemByCartId() throws
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
        Integer productInitialQuantity = 30;
        productRequest.setInitialQuantity(productInitialQuantity);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setProductName("Sound Bar");
        productRequest2.setProductCategory(ELECTRONIC);
        productRequest2.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest2.setInitialQuantity(productInitialQuantity2);
        productRequest2.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest2);
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
        cartItemService.addCartItem(addToCartRequest);
        AddToCartRequest addToCartRequest2 = new AddToCartRequest();
        addToCartRequest2.setProductId(productResponse2.getId());
        addToCartRequest2.setBuyerId(buyerRegisterResponse.getId());
        cartItemService.addCartItem(addToCartRequest2);

        List<CartItem> cartItemList = cartItemService.findAllCartItemBuyCartId(buyerRegisterResponse.getId());

        assertThat(cartItemList).isNotNull();
        log.info("{}", cartItemList);
    }
}
