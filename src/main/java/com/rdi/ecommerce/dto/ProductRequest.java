package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "Provide product's name")
    private String productName;

    @NotBlank(message = "Provide a description of product")
    @Size(min = 3, message = "Give a proper description of the product")
    private String productDescription;

    @NotBlank(message = "Provide the category for product")
    private Category productCategory;

    @NotNull
    private Long merchantId;

    @NotNull(message = "Provide an initial quantity of product to stock")
    @Positive(message = "The number should be greater than 0")
    private Integer initialQuantity;

    @NotNull
    private BigDecimal pricePerUnit;

    @NotBlank
    private MultipartFile productPicture;
}
