package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Address;
import com.rdi.ecommerce.dto.AddressAddResponse;
import com.rdi.ecommerce.dto.BuyerAddressAddRequest;
import com.rdi.ecommerce.exceptions.AddressNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface AddressService {
    AddressAddResponse addAddress(BuyerAddressAddRequest addressAddRequest) throws BuyerNotFoundException;

    Address getAddressBy(Long buyerId) throws AddressNotFoundException;
}
