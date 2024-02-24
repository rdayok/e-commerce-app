package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.BuyerAddressAddRequest;
import com.rdi.ecommerce.dto.AddressAddResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface AddressService {
    AddressAddResponse add(BuyerAddressAddRequest addressAddRequest) throws BuyerNotFoundException;
}
