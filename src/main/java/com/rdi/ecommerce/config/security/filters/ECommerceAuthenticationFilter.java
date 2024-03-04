package com.rdi.ecommerce.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.ecommerce.config.security.services.JwtService;
import com.rdi.ecommerce.dto.LoginRequest;
import com.rdi.ecommerce.dto.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class ECommerceAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            if (authenticationResult.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
                return authenticationResult;
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authenticationResul
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String token = jwtService.generateAccessToken(authenticationResul.getPrincipal().toString());
        LoginResponse loginResponse = new LoginResponse(token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), loginResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
    ) throws ServletException, IOException {
        super.unsuccessfulAuthentication(request, response, failed);
    }


}
