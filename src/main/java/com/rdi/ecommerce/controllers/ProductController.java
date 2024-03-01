package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.exceptions.MediaUploadException;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> addProduct(@RequestParam("productPicture") MultipartFile productPicture,
                                                      @RequestParam String productName,
                                                      @RequestParam String productDescription,
                                                      @RequestParam String productCategory,
                                                      @RequestParam Long merchantId,
                                                      @RequestParam BigDecimal pricePerUnit,
                                                      @RequestParam int initialQuantity) throws
            MediaUploadException, MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
        System.out.println(productCategory + " "+merchantId + " "+productDescription);
        ProductRequest productRequest = productService.buildProductRequest(
                productPicture, productCategory, productDescription,
                productName, merchantId, pricePerUnit, initialQuantity
        );
        return ResponseEntity.status(CREATED).body(productService.addProduct(productRequest));
    }
}
