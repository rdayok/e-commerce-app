package com.rdi.ecommerce.controllers;

import com.rdi.ecommerce.dto.AddressAddResponse;
import com.rdi.ecommerce.dto.BuyerAddressAddRequest;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressAddResponse> addAddress(@Valid @RequestBody BuyerAddressAddRequest buyerAddressAddRequest) throws
            BuyerNotFoundException {
        return ResponseEntity.status(CREATED).body(addressService.addAddress(buyerAddressAddRequest));
    }
}
