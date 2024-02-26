package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.dto.PaymentResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.BuyerService;
import com.rdi.ecommerce.services.CartItemService;
import com.rdi.ecommerce.services.CartService;
import com.rdi.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ECommerceOrderService implements OrderService {

    private final CartService cartService;
    private final CartItemService cartItemService;
    @Override
    public PaymentResponse initialisePayment(Long buyerId) throws BuyerNotFoundException {
        Cart cart = cartService.findByBuyerId(buyerId);
        List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
        return null;
    }
}
