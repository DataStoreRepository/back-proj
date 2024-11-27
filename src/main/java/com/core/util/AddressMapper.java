package com.core.util;

import com.core.dto.AddressDTO;
import com.core.entity.Address;

public abstract class AddressMapper {

    public static AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo!");
        }

        AddressDTO addressDTO = new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());

        return addressDTO;

    }

    public static Address toAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            throw new IllegalArgumentException("AddressDTO não pode ser null");
        }
        // Conversão do DTO para a entidade Address
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setNeighborhood(addressDTO.getNeighborhood());
        return address;
    }

}
