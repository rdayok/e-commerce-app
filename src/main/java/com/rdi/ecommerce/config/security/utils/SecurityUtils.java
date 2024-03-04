package com.rdi.ecommerce.config.security.utils;

import java.util.List;

public class SecurityUtils {



    public static List<String> getPublicEndPoints() {
        return List.of(
                "/login",
                "/api/v1/product"
        );
    }
}
