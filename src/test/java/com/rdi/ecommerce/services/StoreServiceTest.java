package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class StoreServiceTest {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private StoreService storeService;

    @Test
    public void testGetStoreById() throws StoreNotFoundException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);

        Store gottenStore = storeService.getStoreBy(merchantRegisterResponse.getStore().getId());

        assertThat(gottenStore).isNotNull();
    }


}
