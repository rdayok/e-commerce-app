package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.data.repository.UserRepository;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.dto.UserRegisterResponse;
import com.rdi.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceUserService implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        User registeredUser = userRepository.save(user);
        return modelMapper.map(registeredUser, UserRegisterResponse.class);
    }

}
