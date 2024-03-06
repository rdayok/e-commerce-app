package com.rdi.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Slf4j
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddCart() throws UnsupportedEncodingException, JsonProcessingException {
        String BUYER_URL = "/api/v1/buyer";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("dayokr@gmail.com");
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
            log.info("Error ::", exception);
        }
        assert buyerRegistrationMvcResult != null;
        String buyerRegistrationResponseAsString = buyerRegistrationMvcResult.getResponse().getContentAsString();
        BuyerRegisterResponse buyerRegistrationResponse =
                objectMapper.readValue(buyerRegistrationResponseAsString, BuyerRegisterResponse.class);

        String buyerToken = buyerRegistrationResponse.getToken();
        String CART_URL = "/api/v1/cart";
        Long buyerId = buyerRegistrationResponse.getId();
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", CART_URL, buyerId))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }
}
