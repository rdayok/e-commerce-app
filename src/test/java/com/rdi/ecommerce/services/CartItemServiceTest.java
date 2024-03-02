package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static com.rdi.ecommerce.services.CloudServiceTest.getTestFile;
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
    public void testAddCartItem() throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException,
            BuyerNotFoundException, ProductNotFoundException,
            ProductInventoryNotFoundException, MediaUploadException
    {
        MerchantRegisterResponse merchantRegisterResponse = registerMerchant("dayokr@gmail.com");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("max_ret@yahoo.com");
        assertThat(buyerRegisterResponse).isNotNull();
        createCart(buyerRegisterResponse);

        AddToCartRequest addToCartRequest = getAddToCartRequest(productResponse, buyerRegisterResponse);
        ApiResponse<?> response = cartItemService.addCartItem(addToCartRequest);

        assertThat(response).isNotNull();
        log.info("{}", response);
    }



    @Test
    public void testRemoveCartItem() throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException,
            BuyerNotFoundException, ProductNotFoundException, ProductInventoryNotFoundException,
            CannotTakeOutCartItemThatDoesNotExistInYOurCartException, MediaUploadException
    {
        MerchantRegisterResponse merchantRegisterResponse = registerMerchant("dayokffssr@gmail.com");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("maxret@yahoo.com");
        assertThat(buyerRegisterResponse).isNotNull();
        createCart(buyerRegisterResponse);
        AddToCartRequest addToCartRequest = getAddToCartRequest(productResponse, buyerRegisterResponse);
        ApiResponse<?> addToCartResponse = cartItemService.addCartItem(addToCartRequest);
        assertThat(addToCartResponse).isNotNull();

        ApiResponse<?> removeCartItemResponse = cartItemService.removeCartItem(addToCartRequest);

        assertThat(removeCartItemResponse).isNotNull();
        log.info("{}", removeCartItemResponse);
    }



    @Test
    public void testFindAllCartItemByCartId() throws
            MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, BuyerNotFoundException,
            ProductNotFoundException, ProductInventoryNotFoundException, MediaUploadException {

        MerchantRegisterResponse merchantRegisterResponse = registerMerchant("dayok@gmail.com");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        productRequest.setProductName("Sound Bar");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest.setInitialQuantity(productInitialQuantity2);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("ret@yahoo.com");
        assertThat(buyerRegisterResponse).isNotNull();
        createCart(buyerRegisterResponse);
        AddToCartRequest addToCartRequest = getAddToCartRequest(productResponse, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest);
        AddToCartRequest addToCartRequest2 = getAddToCartRequest(productResponse2, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest2);

        List<CartItem> cartItemList = cartItemService.findAllCartItemBuyCartId(buyerRegisterResponse.getId());

        assertThat(cartItemList).isNotNull();
        log.info("{}", cartItemList);
    }

    private static AddToCartRequest getAddToCartRequest(ProductResponse productResponse, BuyerRegisterResponse buyerRegisterResponse) {
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProductId(productResponse.getId());
        addToCartRequest.setBuyerId(buyerRegisterResponse.getId());
        return addToCartRequest;
    }

    private BuyerRegisterResponse registerBuyer(String mail) {
        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
        userRegisterRequestForBuyer.setEmail(mail);
        userRegisterRequestForBuyer.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.registerBuyer(buyerRegisterRequest);
        return buyerRegisterResponse;
    }

    private void createCart(BuyerRegisterResponse buyerRegisterResponse) throws BuyerNotFoundException {
        CartResponse cartResponse = cartService.createCart(buyerRegisterResponse.getId());
    }

    private MerchantRegisterResponse registerMerchant(String mail) {
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
