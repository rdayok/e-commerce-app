package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.CartRepository;
import com.rdi.ecommerce.dto.AddToCartRequest;
import com.rdi.ecommerce.dto.ApiResponse;
import com.rdi.ecommerce.dto.CartRequest;
import com.rdi.ecommerce.dto.CartResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.BuyerService;
import com.rdi.ecommerce.services.CartService;
import com.rdi.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ECommerceCartService implements CartService {

    private final BuyerService buyerService;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartResponse createCart(Long buyerId) throws BuyerNotFoundException {
        Cart newCart = setCartData(buyerId);
        Cart createdCart = cartRepository.save(newCart);
        return modelMapper.map(createdCart, CartResponse.class);
    }

    private Cart setCartData(Long buyerId) throws BuyerNotFoundException {
        Buyer gottenBuyer = buyerService.getBuyerBy(buyerId);
        Cart newCart = new Cart();
        newCart.setBuyer(gottenBuyer);
        return newCart;
    }


    @Override
    public Cart findByBuyerId(Long buyerId) {
        return cartRepository.findByBuyerId(buyerId);
    }

}
