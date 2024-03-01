package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.services.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @PostMapping
    public ResponseEntity<MerchantRegisterResponse> registerMerchant(@Valid @RequestBody MerchantRegisterRequest merchantRegisterRequest) {
        return ResponseEntity.status(CREATED).body(merchantService.register(merchantRegisterRequest));
    }
}
