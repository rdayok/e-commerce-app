package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Merchant;
import com.rdi.ecommerce.data.model.Product;
import com.rdi.ecommerce.data.model.ProductInventory;
import com.rdi.ecommerce.data.model.Store;
import com.rdi.ecommerce.data.repository.ProductRepository;
import com.rdi.ecommerce.dto.GetAllProductRequest;
import com.rdi.ecommerce.dto.GetAllProductsResponse;
import com.rdi.ecommerce.dto.ProductRequest;
import com.rdi.ecommerce.dto.ProductResponse;
import com.rdi.ecommerce.exceptions.MediaUploadException;
import com.rdi.ecommerce.exceptions.MerchantNotFoundException;
import com.rdi.ecommerce.exceptions.ProductNotFoundException;
import com.rdi.ecommerce.services.CloudService;
import com.rdi.ecommerce.services.MerchantService;
import com.rdi.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ECommerceProductService implements ProductService {

    private final MerchantService merchantService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CloudService cloudService;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) throws
            MerchantNotFoundException, MediaUploadException {
        Long merchantId = productRequest.getMerchantId();
        Merchant foundMerchant = merchantService.getMerchantBy(merchantId);
        Store merchantStore = foundMerchant.getStore();
        Product product = setProductData(productRequest, merchantStore);
        Product addedProduct = productRepository.save(product);
        return modelMapper.map(addedProduct, ProductResponse.class);
    }

    private Product setProductData(ProductRequest productRequest, Store merchantStore) throws MediaUploadException {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductCategory(productRequest.getProductCategory());
        product.setStore(merchantStore);
        ProductInventory productInventory = new ProductInventory();
        productInventory.setAvailableQuantity(productRequest.getInitialQuantity());
        product.setProductInventory(productInventory);
        product.setPricePerUnit(productRequest.getPricePerUnit());
        String productPictureUrl = cloudService.upload(productRequest.getProductPicture());
        product.setProductPicture(productPictureUrl);
        return product;
    }


    @Override
    public Product getProductBy(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(
                        String.format("Product with the id %d does not exist", productId)
                ));
    }

    @Override
    public List<GetAllProductsResponse> getAllProducts(GetAllProductRequest getAllProductRequest) {
        Pageable pageable = PageRequest.of(getAllProductRequest.getPageNumber() - 1, getAllProductRequest.getPageSize());
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> productList = productPage.getContent();
        return setAllProductsResponseData(productList);
    }

    private static List<GetAllProductsResponse> setAllProductsResponseData(List<Product> productList) {
        return productList.stream().map(product -> {
            GetAllProductsResponse getAllProductsResponse = new GetAllProductsResponse();
            getAllProductsResponse.setProductName(product.getProductName());
            getAllProductsResponse.setId(product.getId());
            getAllProductsResponse.setShopName(product.getStore().getStoreName());
            getAllProductsResponse.setDescription(product.getProductDescription());
            getAllProductsResponse.setProductCategory(product.getProductCategory());
            getAllProductsResponse.setProductPicture(product.getProductPicture());
            getAllProductsResponse.setPricePerUnit(product.getPricePerUnit());
            return getAllProductsResponse;
        }).toList();
    }

}
