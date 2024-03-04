package com.rdi.ecommerce.config.security.filters;

import com.rdi.ecommerce.config.security.services.JwtService;
import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import static com.rdi.ecommerce.config.security.utils.SecurityUtils.getPublicEndPoints;

@Component
@RequiredArgsConstructor
public class ECommerceAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isRequestToPublicEndPoint = request.getMethod().equals("POST") && getPublicEndPoints().contains(request.getServletPath());
        if (isRequestToPublicEndPoint) filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String username = jwtService.extractUsernameFrom(token);
            User user = userService.getUserBy(username);
            var authorities = user.getRole().stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .toList();
            var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
}

