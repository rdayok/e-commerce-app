package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.config.security.services.JwtService;
import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.data.repository.BuyerRepository;
import com.rdi.ecommerce.dto.BuyerRegisterRequest;
import com.rdi.ecommerce.dto.BuyerRegisterResponse;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.enums.Role;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.BuyerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rdi.ecommerce.enums.Role.BUYER;

@Service
@RequiredArgsConstructor
public class ECommerceBuyerService implements BuyerService {

    private final ModelMapper modelMapper;
    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public BuyerRegisterResponse registerBuyer(BuyerRegisterRequest buyerRegisterRequest) {
        UserRegisterRequest userRegisterRequest = buyerRegisterRequest.getUserRegisterRequest();
        User user = modelMapper.map(userRegisterRequest, User.class);
        user.setPassword(passwordEncoder.encode(buyerRegisterRequest.getUserRegisterRequest().getPassword()));
        Buyer registeredBuyer = setBuyerData(buyerRegisterRequest, user);
        BuyerRegisterResponse buyerRegisterResponse = modelMapper.map(registeredBuyer, BuyerRegisterResponse.class);
        String jwtToken = jwtService.generateAccessToken(registeredBuyer.getUser().getEmail());
        buyerRegisterResponse.setToken(jwtToken);
        return buyerRegisterResponse;
    }

    private Buyer setBuyerData(BuyerRegisterRequest buyerRegisterRequest, User user) {
        user.setRole(BUYER);
        Buyer buyer = new Buyer();
        buyer.setUser(user);
        buyer.setPhoneNumber(buyerRegisterRequest.getPhoneNumber());
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer getBuyerBy(Long buyerId) throws BuyerNotFoundException {
        return buyerRepository.findById(buyerId).orElseThrow(() ->
                new BuyerNotFoundException(String.format("The buyer with id %d does not exist", buyerId)));
    }

}
