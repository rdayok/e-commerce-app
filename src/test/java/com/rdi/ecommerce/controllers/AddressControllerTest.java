package com.rdi.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.ecommerce.dto.BuyerAddressAddRequest;
import com.rdi.ecommerce.dto.BuyerRegisterRequest;
import com.rdi.ecommerce.dto.BuyerRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddAddress() throws UnsupportedEncodingException, JsonProcessingException {
        String BUYER_URL = "/api/v1/buyer";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("max_ret@yahoo.com");
        userRegisterRequest.setPassword("secretekey");
        BuyerRegisterRequest buyerRegisterRequest = new BuyerRegisterRequest();
        buyerRegisterRequest.setUserRegisterRequest(userRegisterRequest);
        buyerRegisterRequest.setPhoneNumber("07031005737");
        MvcResult buyerRegistrationMvcResult = null;
        try {
            buyerRegistrationMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(BUYER_URL)
                                    .content(objectMapper.writeValueAsString(buyerRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        assert buyerRegistrationMvcResult != null;
        String buyerRegistrationResponseAsString = buyerRegistrationMvcResult.getResponse().getContentAsString();
        BuyerRegisterResponse buyerRegistrationResponse =
                objectMapper.readValue(buyerRegistrationResponseAsString, BuyerRegisterResponse.class);

        String buyerToken = buyerRegistrationResponse.getToken();
        BuyerAddressAddRequest buyerAddressAddRequest = new BuyerAddressAddRequest();
        buyerAddressAddRequest.setBuildingNumber(1L);
        buyerAddressAddRequest.setStreet("Du");
        buyerAddressAddRequest.setCity("Jos");
        buyerAddressAddRequest.setState("Plateau");
        buyerAddressAddRequest.setBuyerId(buyerRegistrationResponse.getId());
        String ADDRESS_URL = "/api/v1/address";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(ADDRESS_URL)
                                    .content(objectMapper.writeValueAsString(buyerAddressAddRequest))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
