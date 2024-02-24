package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.data.repository.ProductRepository;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.exceptions.MerchantIsNotOwnerOfStoreException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.exceptions.StoreNotFoundException;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.ProductService;
import com.rdi.ecommerce.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceProductService implements ProductService {

    private final MerchantService merchantService;
    private final ModelMapper modelMapper;

    private final StoreService storeService;

    private final ProductRepository productRepository;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) throws
            StoreNotFoundException, MerchantNotFoundException, MerchantIsNotOwnerOfStoreException {
        Long merchantId = productRequest.getMerchantId();
        Long storeId = productRequest.getStoreId();
        Merchant foundMerchant = merchantService.getMerchantBy(storeId);
        if(!merchantId.equals(foundMerchant.getId()))
            throw new MerchantIsNotOwnerOfStoreException(
                    String.format("The merchant with id %d trying to add product " +
                            "to store is not the owner of store", merchantId
                    ));
        Store store = storeService.getStoreBy(storeId);
//        Product product = new Product();
//        product.setProductCategory(productRequest.getProductCategory());
//        product.setProductName(productRequest.getProductName());
//        product.setProductDescription(productRequest.getProductDescription());
        Product product = modelMapper.map(productRequest, Product.class);
        product.setStore(store);
        Product addedProduct = productRepository.save(product);
        return modelMapper.map(addedProduct, ProductResponse.class);
    }

    @Override
    public Product getProductBy(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(
                        String.format("Product with the id %d does not exist", productId)
                ));
    }

}