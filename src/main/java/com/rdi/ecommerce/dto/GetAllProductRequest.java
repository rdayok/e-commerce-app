package com.rdi.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllProductRequest {
    @Positive(message = "Please you can only give a positive number")
    private Integer pageSize;
    @Positive(message = "Please you can only give a positive number")
    private Integer pageNumber;
}
