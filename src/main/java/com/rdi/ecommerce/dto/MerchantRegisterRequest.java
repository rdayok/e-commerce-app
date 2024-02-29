package com.rdi.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchantRegisterRequest {

    @Valid
    @NotNull
    private UserRegisterRequest userRegisterRequest;
    @NotBlank
    @Size(min = 3)
    private String storeName;
}
