package com.rdi.ecommerce.config;

import com.rdi.ecommerce.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> getUserByUsername(userService, username);
    }

    private UserDetails getUserByUsername(UserService userService, String username) {
        var user = userService.getUserBy(username);
        var authorities = user.getRole();
        var userAuthorities =
                authorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.name()))
                        .toList();
        return new User(username, user.getPassword(), userAuthorities);
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ModelMapper createModelMapperBean() {
        return new ModelMapper();
    }
}
