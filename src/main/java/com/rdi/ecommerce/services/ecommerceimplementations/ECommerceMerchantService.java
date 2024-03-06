package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.config.security.services.JwtService;
import com.rdi.ecommerce.data.model.Inventory;
import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.data.repository.MerchantRepository;
import com.rdi.ecommerce.dto.MerchantRegisterRequest;
import com.rdi.ecommerce.dto.MerchantRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.services.MerchantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rdi.ecommerce.enums.Role.MERCHANT;

@Service
@RequiredArgsConstructor
public class ECommerceMerchantService implements MerchantService {

    private final ModelMapper modelMapper;
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public MerchantRegisterResponse register(MerchantRegisterRequest merchantRegisterRequest) {
        Merchant merchant = setMerchantData(merchantRegisterRequest);
        Merchant registeredMerchant = merchantRepository.save(merchant);
        String jwtToken = jwtService.generateAccessToken(registeredMerchant.getUser().getEmail());
        MerchantRegisterResponse merchantRegisterResponse =
                modelMapper.map(registeredMerchant, MerchantRegisterResponse.class);
        merchantRegisterResponse.setJwtToken(jwtToken);
        return merchantRegisterResponse;
    }

    private Merchant setMerchantData(MerchantRegisterRequest merchantRegisterRequest) {
        UserRegisterRequest userRegisterRequest = merchantRegisterRequest.getUserRegisterRequest();
        User user = setUserData(userRegisterRequest);
        Merchant merchant = new Merchant();
        merchant.setUser(user);
        String storeName = merchantRegisterRequest.getStoreName();
        Store store = setStoreData(storeName);
        merchant.setStore(store);
        return merchant;
    }

    private static Store setStoreData(String storeName) {
        Store store = new Store();
        store.setStoreName(storeName);
        return store;
    }

    private User setUserData(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setRole(List.of(MERCHANT));
        return user;
    }

    @Override
    public Merchant getMerchantBy(Long merchantId) throws MerchantNotFoundException {
        return merchantRepository.findById(merchantId).orElseThrow(() ->
                new MerchantNotFoundException(String.format("The merchant with id %d does not exist", merchantId)));
    }

}
