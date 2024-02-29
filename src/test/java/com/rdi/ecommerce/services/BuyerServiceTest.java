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
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("dayokr@gmail.com");

        assertThat(buyerRegisterResponse).isNotNull();
        log.info("{}", buyerRegisterResponse);
    }

    @Test
    public void testGetBuyerById() throws BuyerNotFoundException {
        BuyerRegisterResponse buyerRegisterResponse = registerBuyer("max_ret@yahoo.com");
        assertThat(buyerRegisterResponse).isNotNull();

        Buyer buyer = buyerService.getBuyerBy(buyerRegisterResponse.getId());

        assertThat(buyer).isNotNull();
        log.info("{}", buyer);
    }

    private BuyerRegisterResponse registerBuyer(String mail) {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail(mail);
        userRegisterRequest.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequest);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        BuyerRegisterResponse buyerRegisterResponse = buyerService.registerBuyer(buyerRegisterRequest);
        return buyerRegisterResponse;
    }
}
