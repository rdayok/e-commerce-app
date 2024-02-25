package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.enums.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "Provide product's name")
    private String productName;

    @NotBlank(message = "Provide a description of product")
    private String productDescription;

    @NotBlank(message = "Provide the category for product")
    private Category productCategory;

    private Long merchantId;
    @NotNull(message = "Provide an initial quantity of product to stock")
    @Positive(message = "The number should be greater than 0")
    private Integer initialQuantity;
}
