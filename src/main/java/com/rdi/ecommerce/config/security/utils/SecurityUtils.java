package com.rdi.ecommerce.config.security.utils;

import java.util.List;

public class SecurityUtils {


    public static List<String> getPublicPostEndPoints() {
        return List.of(
                "/login",
                "/api/v1/merchant",
                "/api/v1/buyer"
        );
    }

    public static List<String> getPublicGetEndPoints() {
        return List.of(
                "/api/v1/product"
        );
    }


    public static String[] getBuyerPostEndPoints() {
        return new String[]{
                "/api/v1/address",
                "/api/v1/cart/**",
                "/api/v1/cart_item/**",
                "/api/v1/inventory/reserve/{productInventoryId}",
                "/api/v1/inventory/return/{productInventoryId}",
                "/api/v1/order/**"
        };
    }

    public static String[] getBuyerGetEndPoints() {
        return new String[]{
                "/api/v1/cart_item/**"
        };
    }


    public static String[] getMerchantPostEndPoints() {
        return new String[]{
                "/api/v1/inventory/restock",
                "/api/v1/product"
        };
    }

//    public static String[] getMerchantGetEndPoints() {
//        return new String[]{};
//    }

}
