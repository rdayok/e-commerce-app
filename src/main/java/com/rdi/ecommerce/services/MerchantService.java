package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;

public interface MerchantService {

    MerchantRegisterResponse register(MerchantRegisterRequest merchantRegisterRequest);

    Merchant getMerchantBy(Long merchantId) throws MerchantNotFoundException;
}
