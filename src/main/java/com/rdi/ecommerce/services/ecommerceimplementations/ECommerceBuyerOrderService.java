package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.*;
import com.rdi.ecommerce.data.repository.OrderRepository;
import com.rdi.ecommerce.dto.*;
import com.rdi.ecommerce.exceptions.AddressNotFoundException;
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
    private final AddressService addressService;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public CheckOutResponse checkOut(Long buyerId) throws BuyerNotFoundException, AddressNotFoundException {
        Cart cart = cartService.findByBuyerId(buyerId);
        Buyer buyer = cart.getBuyer();
        String buyerEmail = buyer.getUser().getEmail();
        AtomicReference<BigDecimal> totalPurchaseCost = new AtomicReference<>(BigDecimal.ZERO);
        List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
        List<OrderItem> orderItemsList = new ArrayList<>();
        createOrderItemsFromCartItems(cartItems, totalPurchaseCost, orderItemsList);
        PayStackPaymentResponse paymentResponse = makePaymentForPurchase(totalPurchaseCost, buyerEmail);
        PaymentReceipt paymentReceipt = createPaymentReceipt(paymentResponse);
        BuyerOrder buyerOrder = createBuyerOrder(buyer, paymentReceipt, orderItemsList);
        BuyerOrder createdOrder = orderRepository.save(buyerOrder);
        return new CheckOutResponse(createdOrder.getId(), paymentResponse.getData().getReference());
    }

    private BuyerOrder createBuyerOrder(Buyer buyer,
                                        PaymentReceipt paymentReceipt,
                                        List<OrderItem> orderItemsList) throws AddressNotFoundException {
        BuyerOrder buyerOrder = new BuyerOrder();
        buyerOrder.setBuyer(buyer);
        buyerOrder.setPaymentReceipt(paymentReceipt);
        buyerOrder.setOrderItemsList(orderItemsList);
        Address gottenAddress = addressService.getAddressBy(buyer.getUser().getId());
        buyerOrder.setDeliveryAddress(gottenAddress);
        return buyerOrder;
    }

    private PayStackPaymentResponse makePaymentForPurchase(AtomicReference<BigDecimal> totalPurchaseCost,
                                                           String buyerEmail) {
        BigDecimal finalTotalPurchaseCost = totalPurchaseCost.get();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(finalTotalPurchaseCost);
        paymentRequest.setEmail(buyerEmail);
        PayStackPaymentResponse paymentResponse = paymentService.initialisePayment(paymentRequest);
        return paymentResponse;
    }

    private static PaymentReceipt createPaymentReceipt(PayStackPaymentResponse paymentResponse) {
        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setAuthorization_url(paymentResponse.getData().getAuthorization_url());
        paymentReceipt.setAuthorizationStatus(paymentResponse.getStatus());
        paymentReceipt.setMessage(paymentResponse.getMessage());
        paymentReceipt.setReference(paymentResponse.getData().getReference());
        paymentReceipt.setAccess_code(paymentResponse.getData().getAccess_code());
        paymentReceipt.setPaymentStatus("pending");
        return paymentReceipt;
    }


    private static void createOrderItemsFromCartItems(List<CartItem> cartItems,
                                                      AtomicReference<BigDecimal> totalPurchaseCost,
                                                      List<OrderItem> orderItemsList) {
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
    }

    @Override
    @Transactional
    public String verifyCheckOutPayment(VerifyingCheckoutPaymentRequest verifyingCheckoutPaymentRequest) throws
            BuyerOrderNotFoundException, BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException {
        String SUCCESSFUL = "successful";
        Long buyerOrderId = verifyingCheckoutPaymentRequest.getBuyerOrderId();
        Long buyerId = verifyingCheckoutPaymentRequest.getBuyerId();
        BuyerOrder buyerOrder = getBuyerOrder(buyerOrderId);
        Buyer buyer = buyerOrder.getBuyer();
        checkIfBuyerOrderIsForBuyer(buyer, buyerId, buyerOrderId);
        String paymentReference = buyerOrder.getPaymentReceipt().getReference();
        VerifyPaymentResponse verifyPaymentResponse = paymentService.verifyPayment(paymentReference);
        String paymentStatus = verifyPaymentResponse.getData().getStatus();
        clearCartItemsIfPaymentIsSuccessful(paymentStatus, SUCCESSFUL, buyerOrder, buyerId);
        return SUCCESSFUL;
    }

    private void clearCartItemsIfPaymentIsSuccessful(String paymentStatus,
                                                     String SUCCESSFUL,
                                                     BuyerOrder buyerOrder,
                                                     Long buyerId) {
        if (Objects.equals(paymentStatus, SUCCESSFUL)) {
            buyerOrder.getPaymentReceipt().setPaymentStatus(SUCCESSFUL);
            orderRepository.save(buyerOrder);
            Cart cart = cartService.findByBuyerId(buyerId);
            List<CartItem> cartItems = cartItemService.findAllCartItemBuyCartId(cart.getId());
            recordProductAreSold(cartItems);
            cartItemService.clearCartItemsFromCart(cartItems);
        }
    }

    private void recordProductAreSold(List<CartItem> cartItems) {
        List<ProductInventory> productInventoryList = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            Integer quantityOfItem = cartItem.getItemQuantity();
            ProductInventory productInventory = cartItem.getProduct().getProductInventory();
            productInventory.recordSold(quantityOfItem);
            productInventoryList.add(productInventory);
        });
        inventoryService.saveAll(productInventoryList);
    }

    private static void checkIfBuyerOrderIsForBuyer(Buyer buyer, Long buyerId, Long buyerOrderId) throws
            BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException {
        if (!Objects.equals(buyer.getId(), buyerId))
            throw new BuyerCannotVerifyPaymentOfBuyerOrderNotTheirsException(
                    String.format("The buyerOrder with id %d is not yours", buyerOrderId)
            );
    }

    private BuyerOrder getBuyerOrder(Long buyerOrderId) throws BuyerOrderNotFoundException {
        return orderRepository.findById(buyerOrderId).orElseThrow(() ->
                new BuyerOrderNotFoundException(
                        String.format("The buyer order with id %d does not exist", buyerOrderId)
                ));
    }

}
