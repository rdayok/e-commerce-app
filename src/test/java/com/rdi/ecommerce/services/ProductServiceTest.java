package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.MediaUploadException;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static com.rdi.ecommerce.enums.Category.ELECTRONIC;
import static com.rdi.ecommerce.services.CloudServiceTest.getTestFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class ProductServiceTest {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProductService productService;

    private ProductRequest productRequest;


    @BeforeEach
    public void setUp() {
        productRequest = new ProductRequest();
        productRequest.setProductName("TV");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("Flat scree 50 inc Lg TV");
        productRequest.setInitialQuantity(5);
        productRequest.setProductPicture(getTestFile());
        productRequest.setPricePerUnit(BigDecimal.valueOf(50000));
    }


    @Test
    public void testAddProduct() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, MediaUploadException {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("dayokr@gmail.com");

        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);

        assertThat(productResponse).isNotNull();
        log.info("{}", productResponse);
    }

    @Test
    public void testAddTwoProducts() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, MediaUploadException {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("retnaamaxwelldayok@gmail.com");

        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        productRequest.setProductName("Sound Bar");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest.setProductPicture(getTestFile());
        productRequest.setInitialQuantity(productInitialQuantity2);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest);

        assertThat(productResponse).isNotNull();
        assertThat(productResponse2).isNotNull();
        log.info("{} {}", productResponse, productResponse2);
    }

    @Test
    public void testGetProductById() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, ProductNotFoundException, MediaUploadException {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("max_ret@yahoo.com");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();

        Long productId = productResponse.getId();
        Product gottenProduct = productService.getProductBy(productId);

        assertThat(gottenProduct).isNotNull();
    }

    @Test
    public void testGetAllProducts() throws MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, MediaUploadException {
        MerchantRegisterResponse merchantRegisterResponse = getMerchantRegisterResponse("retnaadayok@gmail.com");
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse = productService.addProduct(productRequest);
        assertThat(productResponse).isNotNull();
        productRequest.setProductName("Sound Bar");
        productRequest.setProductCategory(ELECTRONIC);
        productRequest.setProductDescription("A black 180W bluetoth sound bar with wofer");
        Integer productInitialQuantity2 = 50;
        productRequest.setProductPicture(getTestFile());
        productRequest.setInitialQuantity(productInitialQuantity2);
        productRequest.setMerchantId(merchantRegisterResponse.getId());
        ProductResponse productResponse2 = productService.addProduct(productRequest);
        assertThat(productResponse2).isNotNull();

        GetAllProductRequest getAllProductRequest = new GetAllProductRequest();
        int numberOfProducts = 2;
        int pageNUmber = 1;
        getAllProductRequest.setPageNumber(pageNUmber);
        getAllProductRequest.setPageSize(numberOfProducts);
        List<GetAllProductsResponse> getAllProductsResponseList = productService.getAllProducts(getAllProductRequest);

        assertThat(getAllProductsResponseList).isNotNull();
        log.info("{}", getAllProductsResponseList);
    }

    private MerchantRegisterResponse getMerchantRegisterResponse(String mail) {
        UserRegisterRequest userRegisterRequestForMerchant = new UserRegisterRequest();
        userRegisterRequestForMerchant.setEmail(mail);
        userRegisterRequestForMerchant.setPassword("secretekey");
        MerchantRegisterRequest merchantRegisterRequest = new MerchantRegisterRequest();
        merchantRegisterRequest.setUserRegisterRequest(userRegisterRequestForMerchant);
        merchantRegisterRequest.setStoreName("wadrobe");
        MerchantRegisterResponse merchantRegisterResponse = merchantService.register(merchantRegisterRequest);
        assertThat(merchantRegisterResponse).isNotNull();
        return merchantRegisterResponse;
    }


}

