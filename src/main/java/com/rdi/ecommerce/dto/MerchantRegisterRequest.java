package com.rdi.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchantRegisterRequest {
    private UserRegisterRequest userRegisterRequest;
    private String storeName;
}
