package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllProductRequest {
    private Integer pageSize;
    private Integer pageNumber;
}
