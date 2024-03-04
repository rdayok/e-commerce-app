package com.rdi.ecommerce.config.security;

import com.rdi.ecommerce.config.security.filters.ECommerceAuthenticationFilter;
import com.rdi.ecommerce.config.security.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        return httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
////                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .cors(httpSecurityCorsConfigurer -> {
////                    CorsConfiguration corsConfiguration = new CorsConfiguration();
////                    corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "GET"));
////                    corsConfiguration.setAllowedOrigins(List.of("*"));
////                })
////                .addFilterAt(new ECommerceAuthenticationFilter(authenticationManager, jwtService), UsernamePasswordAuthenticationFilter.class)
////                .addFilterBefore()
//        return null;
//    }

}
