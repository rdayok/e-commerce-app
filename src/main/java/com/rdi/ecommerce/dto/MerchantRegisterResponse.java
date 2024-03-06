package com.rdi.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantRegisterResponse {
    private Long id;
    private String jwtToken;
    private StoreResponse store;
}
