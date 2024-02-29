package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.OrderRepository;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerOrderNotFoundException;
import com.rdi.ecommerce.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ECommerceBuyerOrderService implements BuyerOrderService {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public CheckOutResponse checkOut(Long buyerId) throws BuyerNotFoundException {
        Cart cart = cartService.findByBuyerId(buyerId);
        Buyer buyer = cart.getBuyer();
        String buyerEmail = buyer.getUser().getEmail();
        List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
        AtomicReference<BigDecimal> totalPurchaseCost = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderItem> orderItemsList = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            Product product = cartItem.getProduct();
            BigDecimal pricePerUnit = product.getPricePerUnit();
            Integer itemQuantity = cartItem.getItemQuantity();
            BigDecimal costOfItem = pricePerUnit.multiply(BigDecimal.valueOf(itemQuantity));
            totalPurchaseCost.updateAndGet(currentTotal -> currentTotal.add(costOfItem));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setItemQuantity(cartItem.getItemQuantity());
            orderItem.setCostOfItem(costOfItem);
            orderItemsList.add(orderItem);
        });
        BigDecimal finalTotalPurchaseCost = totalPurchaseCost.get();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(finalTotalPurchaseCost);
        paymentRequest.setEmail(buyerEmail);
        PayStackPaymentResponse paymentResponse = paymentService.initialisePayment(paymentRequest);
        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setAuthorization_url(paymentResponse.getData().getAuthorization_url());
        paymentReceipt.setAuthorizationStatus(paymentResponse.getStatus());
        paymentReceipt.setMessage(paymentResponse.getMessage());
        paymentReceipt.setReference(paymentResponse.getData().getReference());
        paymentReceipt.setAccess_code(paymentResponse.getData().getAccess_code());
        paymentReceipt.setPaymentStatus("pending");
        BuyerOrder buyerOrder = new BuyerOrder();
        buyerOrder.setBuyer(buyer);
        buyerOrder.setPaymentReceipt(paymentReceipt);
        buyerOrder.setOrderItemsList(orderItemsList);
        // TODO -> set delivery address for the buyer order
        BuyerOrder createdOrder = orderRepository.save(buyerOrder);
        System.out.println(createdOrder);
        return new CheckOutResponse(createdOrder.getId(), paymentResponse.getData().getReference());
    }

    @Override
    @Transactional
    public Object verifyCheckOutPayment(VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest) throws BuyerOrderNotFoundException, BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException {
        String SUCCESSFUL = "successful";
        Long buyerOrderId = verifyingCheckoutPaymentRequest.getBuyerOrderId();
        Long buyerId = verifyingCheckoutPaymentRequest.getBuyerId();
        BuyerOrder buyerOrder = orderRepository.findById(buyerOrderId).orElseThrow(() ->
                new BuyerOrderNotFoundException(
                        String.format("The buyer order with id %d does not exist", buyerOrderId)
                ));
        Buyer buyer = buyerOrder.getBuyer();
        if (!Objects.equals(buyer.getId(), buyerId))
            throw new BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException(
                    String.format("The buyerOrder with id %d is not yours", buyerOrderId)
            );
        String paymentReference = buyerOrder.getPaymentReceipt().getReference();
        VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment(paymentReference);
        String paymentStatus = verifyPaymentResponse.getData().getStatus();
        BuyerOrder savedBuyerOrder = null;
        if (Objects.equals(paymentStatus, SUCCESSFUL)) {
            buyerOrder.getPaymentReceipt().setPaymentStatus(SUCCESSFUL);
            savedBuyerOrder = orderRepository.save(buyerOrder);
            Cart cart = cartService.findByBuyerId(buyerId);
            List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
            cartItemService.clearCartItemsFromCart(cartItems);
        }
        // TODO -> Change my return to a buyer order response with all the items payment details and delivery address,
        return SUCCESSFUL;
    }


}
