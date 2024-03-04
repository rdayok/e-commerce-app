package com.rdi.ecommerce.config.security.manager;

import com.rdi.ecommerce.exceptions.UserAuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ECommerceAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if(authenticationProvider.supports(authentication.getClass()))
            return authenticationProvider.authenticate(authentication);
        throw new UserAuthenticationFailedException("Unable to authenticate request of this type");
    }
}
