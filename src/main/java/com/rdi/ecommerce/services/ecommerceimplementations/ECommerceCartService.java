package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.data.model.Cart;
import com.rdi.ecommerce.data.model.Item;
import com.rdi.ecommerce.data.model.Product;
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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ECommerceCartService implements CartService {
//
//    private final BuyerService buyerService;
//    private final CartRepository cartRepository;
//    private final ModelMapper modelMapper;
//    private final ProductService productService;
//    @Override
//    public CartResponse createCart(CartRequest cartRequest) throws BuyerNotFoundException {
//        Long buyerId = cartRequest.getBuyerId();
//        Buyer gottenBuyer = buyerService.getBuyerBy(buyerId);
//        Cart cart = cartRepository.findByBuyerId(buyerId);
//        if(cart != null) return modelMapper.map(cart, CartResponse.class);
//        Cart newCart = new Cart();
//        newCart.setBuyer(gottenBuyer);
//        Cart createdCart = cartRepository.save(newCart);
//        return modelMapper.map(createdCart, CartResponse.class);
//    }
//
//    @Override
//    public ApiResponse<?> addToCart(AddToCartRequest addToCartRequest)
//            throws BuyerNotFoundException, ProductNotFoundException {
//        Long buyerId = addToCartRequest.getBuyerId();
//        Long productId = addToCartRequest.getProductId();
//        int quantityOfProduct = addToCartRequest.getQuantityOfProduct();
//        Buyer gottenBuyer = buyerService.getBuyerBy(buyerId);
//        Product gottenProduct = productService.getProductBy(productId);
//        Cart foundCart = cartRepository.findByBuyerId(buyerId);
//        int totalAvailableProduct = gottenProduct.getQuantity();
//        gottenProduct.setQuantity(totalAvailableProduct - quantityOfProduct);
//        Item item = new Item();
//        item.setQuantity(quantityOfProduct);
//        item.setProduct(gottenProduct);
//
//        return null;
//    }

}
