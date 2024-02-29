package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.dto.BuyerRegisterRequest;
import com.rdi.ecommerce.dto.BuyerRegisterResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;

public interface BuyerService {
    BuyerRegisterResponse registerBuyer(BuyerRegisterRequest buyerRegisterRequest);

    Buyer getBuyerBy(Long buyerId) throws BuyerNotFoundException;
}
