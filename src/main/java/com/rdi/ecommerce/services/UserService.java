package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.dto.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

}
