package com.rdi.ecommerce.services;

import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.dto.UserRegisterResponse;
import com.rdi.ecommerce.enums.Role;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    User getUserBy(String username);

    User getSuperAdmin();

    void initialiseSuperAdmin(UserRegisterRequest userRegisterRequest);
}
