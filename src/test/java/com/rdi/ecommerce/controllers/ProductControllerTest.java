package com.rdi.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.services.CloudServiceTest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static com.rdi.ecommerce.services.CloudServiceTest.getTestFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddProduct() throws UnsupportedEncodingException, JsonProcessingException {
        String URL = "/api/v1/merchant";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MvcResult registerMerchantMvcResult = null;
        try{
            registerMerchantMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        }catch (Exception exception){
            log.info("Error ::", exception);
        }
        assert registerMerchantMvcResult != null;
        String merchantRegistrationResponseAsString = registerMerchantMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);

        log.info("{}", merchantRegisterResponse);
        MultipartFile file = getTestFile();
        Path path = Paths.get(CloudServiceTest.IMAGE_LOCATION);
        String PRODUCT_URL = "/api/v1/product";
        String fileName = path.getFileName().toString();
        Long id = merchantRegisterResponse.getId();
        int quantity = 100;
        Part productName = new MockPart("productName", "TV".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part productDescription = new MockPart("productDescription", "Flat scree 50 inc Lg TV".getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.toString(quantity).getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", BigDecimal.valueOf(5000).toString().getBytes());
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());

        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.multipart(PRODUCT_URL)
                                    .file(new MockMultipartFile("productPicture", file.getInputStream()))
                                    .part(productName)
                                    .part(productDescription)
                                    .part(productCategory)
                                    .part(initialQuantity)
                                    .part(pricePerUnit)
                                    .part(merchantId)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }
}
