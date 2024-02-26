package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.CartItem;
import com.rdi.ecommerce.dto.PaymentRequest;
import com.rdi.ecommerce.dto.PaymentResponse;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ECommerceOrderService implements OrderService {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final PaymentService paymentService;
    @Override
    public PaymentResponse initialisePayment(Long buyerId) throws BuyerNotFoundException {
        Cart cart = cartService.findByBuyerId(buyerId);
        Buyer buyer = cart.getBuyer();
        String buyerEmail = buyer.getUser().getEmail();
        List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
        AtomicReference<BigDecimal> totalPurchaseCost = new AtomicReference<>(BigDecimal.ZERO);
        cartItems.forEach(cartItem -> {
            BigDecimal pricePerUnit = cartItem.getProduct().getPricePerUnit();
            Integer itemQuantity = cartItem.getItemQuantity();
            BigDecimal costOfItem = pricePerUnit.multiply(BigDecimal.valueOf(itemQuantity));
            totalPurchaseCost.updateAndGet(currentTotal -> currentTotal.add(costOfItem));
        });
        BigDecimal finalTotalPurchaseCost = totalPurchaseCost.get();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(finalTotalPurchaseCost);
        paymentRequest.setEmail(buyerEmail);
        PaymentResponse paymentResponse = paymentService.initialisePayment(paymentRequest);
        System.out.println(paymentResponse);
        return paymentResponse;
    }
}
