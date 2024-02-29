package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class MerchantServiceTest {

    @Autowired
    private MerchantService merchantService;

    @Test
    public void testRegisterMerchant() {
        MerchantRegisterRequest merchantRegisterRequest = createMerchantRegistraionRequest("dayokr@gmail.com");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);

        assertThat(merchantRegisterResponse).isNotNull();
        log.info("{}", merchantRegisterResponse);
    }

    @Test
    public void testGetMerchantById() throws MerchantNotFoundException {
        MerchantRegisterRequest merchantRegisterRequest = createMerchantRegistraionRequest("max_ret@yahoo.com");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);

        Long merchantId = merchantRegisterResponse.getId();
        Merchant merchant = merchantService.getMerchantBy(merchantId);

        assertThat(merchant).isNotNull();
    }

    private static MerchantRegisterRequest createMerchantRegistraionRequest(String mail) {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail(mail);
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        return merchantRegisterRequest;
    }
}
