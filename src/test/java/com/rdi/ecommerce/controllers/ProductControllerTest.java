package com.rdi.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.ecommerce.dto.GetAllProductRequest;
import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
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
        String PRODUCT_URL = "/api/v1/product";
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

    @Test
    public void testShowAllECommerceProducts() throws JsonProcessingException, UnsupportedEncodingException {
        String URL = "/api/v1/merchant";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("max_ret@yahoo.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MvcResult registerMerchantMvcResult = null;
        try {
            registerMerchantMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error ::", exception);
        }
        assert registerMerchantMvcResult != null;
        String merchantRegistrationResponseAsString = registerMerchantMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);

        MultipartFile file = getTestFile();
        Long id = merchantRegisterResponse.getId();
        BigDecimal price = BigDecimal.valueOf(200000);
        int quantity = 100;
        String PRODUCT_URL = "/api/v1/product";
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());
        Part productDescription = new
                MockPart("productDescription", "An army green seating room couch".getBytes());
        Part productName = new MockPart("productName", "Couch".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", price.toString().getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.toString(quantity).getBytes());
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.multipart(PRODUCT_URL)
                                    .file(new MockMultipartFile("productPicture", file.getInputStream()))
                                    .part(merchantId)
                                    .part(productDescription)
                                    .part(productName)
                                    .part(productCategory)
                                    .part(pricePerUnit)
                                    .part(initialQuantity)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error ::", exception);
        }

        GetAllProductRequest getAllProductRequest = new GetAllProductRequest();
        Integer numberOfProducts = 1;
        Integer pageNUmber = 1;
        getAllProductRequest.setPageNumber(pageNUmber);
        getAllProductRequest.setPageSize(numberOfProducts);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.get(PRODUCT_URL)
                                    .content(objectMapper.writeValueAsString(getAllProductRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }
}
