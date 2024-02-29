package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class ProductServiceTest {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProductToStore() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayokr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        productRequest.setInitialQuantity(5);
        productRequest.setPricePerUnit(BigDecimal.valueOf(50000));

        ProductResponse productResponse = productService.addProduct(productRequest);

        assertThat(productResponse).isNotNull();
        log.info("{}", productResponse);
    }

    @Test
    public void testAddTwoProductToStore() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("dayr@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        productRequest.setInitialQuantity(5);
        ProductResponse productResponse = productService.addProduct(productRequest);
        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setProductName("Sound Bar");
        productRequest2.setProductCategory(ELECTRONIC);
        productRequest2.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest2.setInitialQuantity(productInitialQuantity2);
        productRequest2.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest2);

        assertThat(productResponse).isNotNull();
        assertThat(productResponse2).isNotNull();
        log.info("{} {}", productResponse, productResponse2);
    }

    @Test
    public void testGetProductById() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, ProductNotFoundException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("max_ret@yahoo.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        productRequest.setInitialQuantity(5);
        ProductResponse productResponse = productService.addProduct(productRequest);

        Long productId = productResponse.getId();
        Product gottenProduct = productService.getProductBy(productId);

        assertThat(gottenProduct).isNotNull();
    }

    @Test
    public void testGetAllProducts() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail("retnaadayok@gmail.com");
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        productRequest.setInitialQuantity(5);
        productRequest.setPricePerUnit(BigDecimal.valueOf(200_000));
        ProductResponse productResponse = productService.addProduct(productRequest);
        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setProductName("Sound Bar");
        productRequest2.setProductCategory(ELECTRONIC);
        productRequest2.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest2.setInitialQuantity(productInitialQuantity2);
        productRequest2.setPricePerUnit(BigDecimal.valueOf(50_000));
        productRequest2.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest2);

        GetAllProductRequest getAllProductRequest = new GetAllProductRequest();
        int numberOfProducts = 2;
        int pageNUmber = 1;
        getAllProductRequest.setPageNumber(pageNUmber);
        getAllProductRequest.setPageSize(numberOfProducts);
        List<GetAllProductsResponse> getAllProductsResponseList = productService.getAllProducts(getAllProductRequest);
        assertThat(getAllProductsResponseList).isNotNull();
        log.info("{}", getAllProductsResponseList);
    }
}
