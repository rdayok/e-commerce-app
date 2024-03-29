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

@SpringBootTest
@Slf4j
public class BuyerOrderServiceTest {

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
    private BuyerOrderService orderService;
    @Autowired
    private AddressService addressService;
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
    public void testCheckOut() throws
            MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, BuyerNotFoundException,
            ProductNotFoundException, ProductInventoryNotFoundException, MediaUploadException, AddressNotFoundException {
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
        createAddressForBuyer(buyerRegisterResponse);
        assertThat(buyerRegisterResponse).isNotNull();
        createCart(buyerRegisterResponse);
        AddCartItemRequest addToCartRequest = getAddToCartRequest(productResponse, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest);
        AddCartItemRequest addToCartRequest2 = getAddToCartRequest(productResponse2, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest2);

        CheckOutResponse checkOutResponse = orderService.checkOut(buyerRegisterResponse.getId());

        assertThat(checkOutResponse).isNotNull();
        log.info("{}", checkOutResponse);
    }

    @Test
    public void testVerifyingPaymentAtCheckout() throws
            BuyerNotFoundException, ProductNotFoundException,
            ProductInventoryNotFoundException, MerchantNotFoundException,
            MerchantIsNotOwnerOfStoreException, BuyerOrderNotFoundException,
            BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException, MediaUploadException, AddressNotFoundException {
        MerchantRegisterResponse merchantRegisterResponse = registerMerchant("dayokr@gmail.com");
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
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("max_ret@yahoo.com");
        createAddressForBuyer(buyerRegisterResponse);
        assertThat(buyerRegisterResponse).isNotNull();
        createCart(buyerRegisterResponse);
        AddCartItemRequest addToCartRequest = getAddToCartRequest(productResponse, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest);
        AddCartItemRequest addToCartRequest2 = getAddToCartRequest(productResponse2, buyerRegisterResponse);
        cartItemService.addCartItem(addToCartRequest2);
        CheckOutResponse checkOutResponse = orderService.checkOut(buyerRegisterResponse.getId());
        assertThat(checkOutResponse).isNotNull();

        VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest = new VerifyingCheckoutPaymentRequest();
        String paymentReference = checkOutResponse.getPaymentReference();
        verifyingCheckoutPaymentRequest.setBuyerOrderId(checkOutResponse.getOrderId());
        verifyingCheckoutPaymentRequest.setBuyerId(buyerRegisterResponse.getId());

        ApiResponse<?> confirmCheckoutPaymentResponse = orderService.verifyCheckOutPayment(verifyingCheckoutPaymentRequest);

        assertThat(confirmCheckoutPaymentResponse).isNotNull();
        log.info("{}", confirmCheckoutPaymentResponse);
    }

    private void createAddressForBuyer(BuyerRegisterResponse buyerRegisterResponse) throws BuyerNotFoundException {
        BuyerAddressAddRequest buyerAddressAddRequest = new BuyerAddressAddRequest();
        buyerAddressAddRequest.setBuildingNumber(2L);
        buyerAddressAddRequest.setStreet("Du");
        buyerAddressAddRequest.setCity("Jos");
        buyerAddressAddRequest.setState("Plateau");
        buyerAddressAddRequest.setBuyerId(buyerRegisterResponse.getId());
        AddressAddResponse addressAddResponse = addressService.addAddress(buyerAddressAddRequest);
        assertThat(addressAddResponse).isNotNull();
    }

    private static AddCartItemRequest getAddToCartRequest(ProductResponse productResponse, BuyerRegisterResponse buyerRegisterResponse) {
        AddCartItemRequest addToCartRequest = new AddCartItemRequest();
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
