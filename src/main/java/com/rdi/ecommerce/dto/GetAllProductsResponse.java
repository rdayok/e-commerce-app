package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class GetAllProductsResponse {
    private Long id;
    private String productName;
    private String description;
    private String shopName;
    private Category productCategory;
    private String productPicture;
    private BigDecimal pricePerUnit;
}
