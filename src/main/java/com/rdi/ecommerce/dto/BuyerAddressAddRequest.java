package com.rdi.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyerAddressAddRequest {

    @Positive(message = "You need to enter a positive number to indicate your house number")
    private Long buildingNumber;

    @NotBlank
    @Size(min = 2, message = "Name of street cannot be less than two characters")
    private String street;
    @NotBlank
    @Size(min = 2, message = "Name of city cannot be less than two characters")
    private String city;
    @NotBlank
    @Size(min = 2, message = "Name of state cannot be less than two characters")
    private String state;

    @JsonProperty("id")
    private Long buyerId;
}
