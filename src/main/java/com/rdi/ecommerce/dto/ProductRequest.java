package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.enums.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "Provide product's name")
    private String productName;

    @NotBlank(message = "Provide a description of product")
    private String productDescription;

    @NotBlank(message = "Provide the category for product")
    private Category productCategory;
    
    @NotNull(message = "Provide the store the product would be added to")
    @Positive(message = "The number should be greater than 0")
    private Long storeId;

    private Long merchantId;
//    @NotNull(message = "Provide an initial quantity of product to stock")
//    @Positive(message = "The number should be greater than 0")
//    private int quantity;
}
