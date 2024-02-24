package com.rdi.ecommerce.services;

import com.rdi.ecommerce.dto.UserRegisterRequest;
import com.rdi.ecommerce.dto.UserRegisterResponse;
import com.rdi.ecommerce.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUserRegistration(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("dayokr@gmail.com");
        userRegisterRequest.setPassword("secretekey");

        UserRegisterResponse userRegisterResponse = userService.register(userRegisterRequest);

        assertThat(userRegisterResponse).isNotNull();
        log.info("{}", userRegisterResponse);
    }

}
