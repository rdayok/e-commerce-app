package com.rdi.ecommerce.config;

import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.enums.Role;
import com.rdi.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class SuperAdminConfig implements CommandLineRunner {
    @Value("${adminEmail}")
    private String adminUsername;
    @Value("${adminPass}")
    private String adminPassword;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        boolean superAdminExist = userService.getSuperAdmin() != null;
        if(superAdminExist) return;
        initialiseSuperAdmin();
    }

    private void initialiseSuperAdmin() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail(adminUsername);
        userRegisterRequest.setPassword(adminPassword);
        userService.initialiseSuperAdmin(userRegisterRequest);
    }
}
