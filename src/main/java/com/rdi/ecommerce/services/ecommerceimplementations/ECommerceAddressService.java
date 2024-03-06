package com.rdi.ecommerce.services.ecommerceimplementations;

import com.rdi.ecommerce.data.model.Address;
import com.rdi.ecommerce.data.model.Buyer;
import com.rdi.ecommerce.data.model.User;
import com.rdi.ecommerce.data.repository.AddressRepository;
import com.rdi.ecommerce.dto.AddressAddResponse;
import com.rdi.ecommerce.dto.BuyerAddressAddRequest;
import com.rdi.ecommerce.exceptions.AddressNotFoundException;
import com.rdi.ecommerce.exceptions.BuyerNotFoundException;
import com.rdi.ecommerce.services.AddressService;
import com.rdi.ecommerce.services.BuyerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ECommerceAddressService implements AddressService {

    private final BuyerService buyerService;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    @Override
    public AddressAddResponse addAddress( BuyerAddressAddRequest addressAddRequest) throws BuyerNotFoundException {
        Long buyerId = addressAddRequest.getBuyerId();
        Buyer buyer = buyerService.getBuyerBy(buyerId);
        User user = buyer.getUser();
        Address address = modelMapper.map(addressAddRequest, Address.class);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressAddResponse.class);
    }

    @Override
    public Address getAddressBy(Long userId) throws AddressNotFoundException {
        return addressRepository.findByUserId(userId).orElseThrow(() ->
                new AddressNotFoundException(String.format("The address for user with id %d does not exist", userId)));
    }

}
