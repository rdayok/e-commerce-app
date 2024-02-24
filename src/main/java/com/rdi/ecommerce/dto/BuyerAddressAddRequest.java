package com.rdi.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyerAddressAddRequest {

    @Min(value = 1, message = "You cannot enter a negative number")
    @Digits(fraction = 0, message = "You cannot enter a fraction", integer = 5)
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
    @Min(value = 1, message = "You cannot enter a negative number")
    @JsonProperty("id")
    private Long buyerId;
}
