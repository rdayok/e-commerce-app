package com.rdi.ecommerce.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
}
