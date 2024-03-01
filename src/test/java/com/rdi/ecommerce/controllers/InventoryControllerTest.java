package com.rdi.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.ecommerce.dto.*;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import static com.rdi.ecommerce.services.CloudServiceTest.getTestFile;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReserveProductByProductInventoryId() throws UnsupportedEncodingException, JsonProcessingException {
        String MERCHANT_URL = "/api/v1/merchant";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");

        MvcResult merchantRegistrationMvcResult = null;
        try {
            merchantRegistrationMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(MERCHANT_URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)

                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        assert merchantRegistrationMvcResult != null;
        String merchantRegistrationResponseAsString = merchantRegistrationMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);
        String PRODUCT_URL = "/api/v1/product";
        MultipartFile file = getTestFile();
        Long id = merchantRegisterResponse.getId();
        int quantity = 50;
        BigDecimal price = BigDecimal.valueOf(75_000);
        Part productName = new MockPart("productName", "TV".getBytes());
        Part productDescription = new MockPart("productDescription", "4k 52 inch flat screen sony".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.valueOf(quantity).toString().getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", price.toString().getBytes());
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());
        MvcResult addProductMvcResult = null;
        try {
            addProductMvcResult = mockMvc.perform(
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
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error ::", exception);
        }
        assert addProductMvcResult != null;
        String productResponseAsString = addProductMvcResult.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(productResponseAsString, ProductResponse.class);
        ProductInventoryResponse productInventoryResponse = productResponse.getProductInventory();
        Long productInventoryId = productInventoryResponse.getId();
        String INVENTORY_URL = "/api/v1/inventory/reserve";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", INVENTORY_URL, productInventoryId))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    public void testReturnReserveProduct() throws UnsupportedEncodingException, JsonProcessingException {
        String MERCHANT_URL = "/api/v1/merchant";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("max_ret@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");

        MvcResult merchantRegistrationMvcResult = null;
        try {
            merchantRegistrationMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(MERCHANT_URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)

                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        assert merchantRegistrationMvcResult != null;
        String merchantRegistrationResponseAsString = merchantRegistrationMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);
        String PRODUCT_URL = "/api/v1/product";
        MultipartFile file = getTestFile();
        Long id = merchantRegisterResponse.getId();
        int quantity = 50;
        BigDecimal price = BigDecimal.valueOf(75_000);
        Part productName = new MockPart("productName", "TV".getBytes());
        Part productDescription = new MockPart("productDescription", "4k 52 inch flat screen sony".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.valueOf(quantity).toString().getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", price.toString().getBytes());
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());
        MvcResult addProductMvcResult = null;
        try {
            addProductMvcResult = mockMvc.perform(
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
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error ::", exception);
        }
        assert addProductMvcResult != null;
        String productResponseAsString = addProductMvcResult.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(productResponseAsString, ProductResponse.class);
        ProductInventoryResponse productInventoryResponse = productResponse.getProductInventory();
        Long productInventoryId = productInventoryResponse.getId();
        String INVENTORY_URL = "/api/v1/inventory/reserve";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", INVENTORY_URL, productInventoryId))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        String INVENTORY_RETURN_URL = "/api/v1/inventory/return";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", INVENTORY_RETURN_URL, productInventoryId))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }
}
