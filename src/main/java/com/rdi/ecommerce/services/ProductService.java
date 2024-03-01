package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.dto.GetAllProductRequest;
import com.rdi.ecommerce.dto.GetAllProductsResponse;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.enums.Category;
import com.rdi.ecommerce.exceptions.MediaUploadException;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest) throws
            MerchantNotFoundException, MerchantIsNotOwnerOfStoreException, MediaUploadException;

    Product getProductBy(Long productId) throws ProductNotFoundException;

    List<GetAllProductsResponse> getAllProducts(GetAllProductRequest getAllProductRequest);

    default ProductRequest buildProductRequest(MultipartFile file, String productCategory,
                                               String productDescription, String productName,
                                               Long merchantId, BigDecimal pricePerUnit,
                                               int initialQuantity){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setMerchantId(merchantId);
        productRequest.setProductName(productName);
        productRequest.setProductCategory(Category.valueOf(productCategory));
        productRequest.setProductDescription(productDescription);
        productRequest.setProductPicture(file);
        productRequest.setInitialQuantity(initialQuantity);
        productRequest.setPricePerUnit(pricePerUnit);
        System.out.println(productRequest);
        return productRequest;
    };

}
