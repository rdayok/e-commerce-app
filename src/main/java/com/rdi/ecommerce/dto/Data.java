package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Data {
    private String authorization_url;
    private String access_code;
    private String reference;
}
