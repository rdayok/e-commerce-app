package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @Autowired
    private BuyerService buyerService;

    @Test
    public void testAddAddress() throws BuyerNotFoundException {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("dayokr@gmail.com");
        userRegisterRequest.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequest);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.registerBuyer(buyerRegisterRequest);

        BuyerAddressAddRequest buyerAddressAddRequest = new BuyerAddressAddRequest();
        buyerAddressAddRequest.setBuildingNumber(2L);
        buyerAddressAddRequest.setStreet("Du");
        buyerAddressAddRequest.setCity("Jos");
        buyerAddressAddRequest.setState("Plateau");
        buyerAddressAddRequest.setBuyerId(buyerRegisterResponse.getId());

        AddressAddResponse addressAddResponse = addressService.add(buyerAddressAddRequest);

        assertThat(addressAddResponse).isNotNull();
        log.info("{}", addressAddResponse);
    }
}
