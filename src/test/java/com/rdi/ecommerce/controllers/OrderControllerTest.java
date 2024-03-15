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
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String merchantToken;
    private String buyerToken;

    @Test
    public void testCheckOut() throws JsonProcessingException, UnsupportedEncodingException {
        String MERCHANT_URL = "/api/v1/merchant";
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
                            MockMvcRequestBuilders.post(MERCHANT_URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
        assert registerMerchantMvcResult != null;
        String merchantRegistrationResponseAsString = registerMerchantMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);

        MultipartFile file = getTestFile();
        merchantToken = merchantRegisterResponse.getJwtToken();
        String PRODUCT_URL = "/api/v1/product";
        Long id = merchantRegisterResponse.getId();
        int quantity = 100;
        Part productName = new MockPart("productName", "TV".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part productDescription = new MockPart("productDescription", "Flat scree 50 inc Lg TV".getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.toString(quantity).getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", BigDecimal.valueOf(50000).toString().getBytes());
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());

        MvcResult addProductResponseMvcResult = null;
        try {
            addProductResponseMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.multipart(PRODUCT_URL)
                                    .file(new MockMultipartFile("productPicture", file.getInputStream()))
                                    .part(productName)
                                    .part(productDescription)
                                    .part(productCategory)
                                    .part(initialQuantity)
                                    .part(pricePerUnit)
                                    .part(merchantId)
                                    .header("Authorization", "Bearer " + merchantToken)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        assert addProductResponseMvcResult != null;
        String addProductResponseAsString = addProductResponseMvcResult.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(addProductResponseAsString, ProductResponse.class);
        String BUYER_URL = "/api/v1/buyer";
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

        buyerToken = buyerRegistrationResponse.getToken();
        Long buyerId = buyerRegistrationResponse.getId();
        BuyerAddressAddRequest buyerAddressAddRequest = new BuyerAddressAddRequest();
        buyerAddressAddRequest.setBuildingNumber(2L);
        buyerAddressAddRequest.setStreet("Du");
        buyerAddressAddRequest.setCity("Jos");
        buyerAddressAddRequest.setState("Plateau");
        buyerAddressAddRequest.setBuyerId(buyerId);
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

        String CART_URL = "/api/v1/cart";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", CART_URL, buyerId))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        String CART_ITEM_URL = "/api/v1/cart_item";
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest();
        addCartItemRequest.setProductId(productResponse.getId());
        addCartItemRequest.setBuyerId(buyerId);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CART_ITEM_URL + "/add")
                                    .content(objectMapper.writeValueAsString(addCartItemRequest))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CART_ITEM_URL + "/add")
                                    .content(objectMapper.writeValueAsString(addCartItemRequest))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        String ORDER_URL = "/api/v1/order";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(ORDER_URL + "/checkout/" + buyerId)
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @Test
    public void testVerifyingPaymentAtCheckout() throws JsonProcessingException, UnsupportedEncodingException {
        String MERCHANT_URL = "/api/v1/merchant";
        ObjectMapper objectMapper = new ObjectMapper();
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayo@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MvcResult registerMerchantMvcResult = null;
        try{
            registerMerchantMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(MERCHANT_URL)
                                    .content(objectMapper.writeValueAsString(merchantRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
        assert registerMerchantMvcResult != null;
        String merchantRegistrationResponseAsString = registerMerchantMvcResult.getResponse().getContentAsString();
        MerchantRegisterResponse merchantRegisterResponse =
                objectMapper.readValue(merchantRegistrationResponseAsString, MerchantRegisterResponse.class);

        MultipartFile file = getTestFile();
        merchantToken = merchantRegisterResponse.getJwtToken();
        String PRODUCT_URL = "/api/v1/product";
        Long id = merchantRegisterResponse.getId();
        int quantity = 100;
        Part productName = new MockPart("productName", "TV".getBytes());
        Part productCategory = new MockPart("productCategory", "ELECTRONIC".getBytes());
        Part productDescription = new MockPart("productDescription", "Flat scree 50 inc Lg TV".getBytes());
        Part initialQuantity = new MockPart("initialQuantity", Integer.toString(quantity).getBytes());
        Part pricePerUnit = new MockPart("pricePerUnit", BigDecimal.valueOf(50000).toString().getBytes());
        Part merchantId = new MockPart("merchantId", id.toString().getBytes());

        MvcResult addProductResponseMvcResult = null;
        try {
            addProductResponseMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.multipart(PRODUCT_URL)
                                    .file(new MockMultipartFile("productPicture", file.getInputStream()))
                                    .part(productName)
                                    .part(productDescription)
                                    .part(productCategory)
                                    .part(initialQuantity)
                                    .part(pricePerUnit)
                                    .part(merchantId)
                                    .header("Authorization", "Bearer " + merchantToken)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        assert addProductResponseMvcResult != null;
        String addProductResponseAsString = addProductResponseMvcResult.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(addProductResponseAsString, ProductResponse.class);

        String BUYER_URL = "/api/v1/buyer";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("maxret@yahoo.com");
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

        buyerToken = buyerRegistrationResponse.getToken();
        Long buyerId = buyerRegistrationResponse.getId();
        BuyerAddressAddRequest buyerAddressAddRequest = new BuyerAddressAddRequest();
        buyerAddressAddRequest.setBuildingNumber(2L);
        buyerAddressAddRequest.setStreet("Du");
        buyerAddressAddRequest.setCity("Jos");
        buyerAddressAddRequest.setState("Plateau");
        buyerAddressAddRequest.setBuyerId(buyerId);
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

        String CART_URL = "/api/v1/cart";
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(String.format("%s/%s", CART_URL, buyerId))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        String CART_ITEM_URL = "/api/v1/cart_item";
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest();
        addCartItemRequest.setProductId(productResponse.getId());
        addCartItemRequest.setBuyerId(buyerId);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CART_ITEM_URL + "/add")
                                    .content(objectMapper.writeValueAsString(addCartItemRequest))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CART_ITEM_URL + "/add")
                                    .content(objectMapper.writeValueAsString(addCartItemRequest))
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        String ORDER_URL = "/api/v1/order";
        MvcResult checkoutResponseMvcResult = null;
        try {
            checkoutResponseMvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post(ORDER_URL + "/checkout/" + buyerId)
                                    .header("Authorization", "Bearer " + buyerToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        assert checkoutResponseMvcResult != null;
        String checkoutResponseAsString = checkoutResponseMvcResult.getResponse().getContentAsString();
        CheckOutResponse checkOutResponse = objectMapper.readValue(checkoutResponseAsString, CheckOutResponse.class);
        VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest = new VerifyingCheckoutPaymentRequest();
        verifyingCheckoutPaymentRequest.setBuyerOrderId(checkOutResponse.getOrderId());
        verifyingCheckoutPaymentRequest.setBuyerId(buyerId);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(ORDER_URL + "/verify_payment")
                                    .content(objectMapper.writeValueAsString(verifyingCheckoutPaymentRequest))
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
