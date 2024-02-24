package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.dto.BuyerRegisterRequest;
import com.rdi.ecommerce.dto.BuyerRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class BuyerServiceTest {

    @Autowired
    private BuyerService buyerService;

    @Test
    public void testRegisterBuyer() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("dayokr@gmail.com");
        userRegisterRequest.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequest);
        buyerRegisterRequest.setPhoneNumber("07031005737");

        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);

        assertThat(buyerRegisterResponse).isNotNull();
        log.info("{}", buyerRegisterResponse);
    }

    @Test
    public void testGetBuyerById() throws BuyerNotFoundException {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("dayokr@gmail.com");
        userRegisterRequest.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequest);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.register(buyerRegisterRequest);

        Buyer buyer = buyerService.getBuyerBy(buyerRegisterResponse.getId());

        assertThat(buyer).isNotNull();
        log.info("{}", buyer);
    }
}
