package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class CartServiceTest {

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    @Test
    public void testCreatCart() throws BuyerNotFoundException {
        UserRegisterRequest userRegisterRequestForBuyer = new UserRegisterRequest();
        userRegisterRequestForBuyer.setEmail("dayokr@gmail.com");
        userRegisterRequestForBuyer.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequestForBuyer);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.registerBuyer(buyerRegisterRequest);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setBuyerId(buyerRegisterResponse.getId());
        CartResponse cartResponse = cartService.createCart(cartRequest);

        assertThat(cartResponse).isNotNull();
        log.info("{}", cartResponse);
    }

}
