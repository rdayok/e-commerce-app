package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.config.security.services.JwtService;
import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.data.repository.UserRepository;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.dto.UserRegisterResponse;
import com.rdi.ecommerce.enums.Role;
import com.rdi.ecommerce.exceptions.UserNotFoundException;
import com.rdi.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.rdi.ecommerce.enums.Role.SUPER_ADMIN;

@Service
@RequiredArgsConstructor
public class ECommerceUserService implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        User registeredUser = userRepository.save(user);
        String token = jwtService.generateAccessToken(registeredUser.getEmail());

        return modelMapper.map(registeredUser, UserRegisterResponse.class);
    }

    @Override
    public User getUserBy(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() ->
                new RuntimeException(String.format("The user with email %s does not exist", username)));
    }

    @Override
    public User getSuperAdmin() {
        return userRepository.findUserByRole(SUPER_ADMIN);
    }

    @Override
    public void initialiseSuperAdmin(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        user.setRole(SUPER_ADMIN);
        userRepository.save(user);
    }

}
