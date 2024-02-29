package com.rdi.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class PaymentRequest {
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "invalid email address")
    private String email;

    @NotNull(message = "Please provide the amount to pay")
    @DecimalMin(value = "0", inclusive = true, message = "Price must be a non-negativ")
    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        int TEN = 100;
        this.amount = amount.multiply(BigDecimal.valueOf(TEN));
    }
}
