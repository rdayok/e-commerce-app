package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Inventory;
import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.data.repository.StoreRepository;
import com.rdi.ecommerce.dto.StoreRequest;
import com.rdi.ecommerce.dto.StoreResponse;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceStoreService implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Store getStoreBy(Long storeId) throws StoreNotFoundException {
        return storeRepository.findById(storeId).orElseThrow(() ->
                new StoreNotFoundException(
                        String.format("The store with id %d does not exist", storeId)
                ));
    }

}
