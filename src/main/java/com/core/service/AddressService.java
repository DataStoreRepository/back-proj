package com.core.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dto.AddressDTO;
import com.core.entity.Address;
import com.core.repository.AddressRepository;
import com.core.util.AddressMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {
    

    @Autowired
    AddressRepository repository;

    public AddressDTO save(AddressDTO addressDTO) {
        Address address = AddressMapper.toAddress(addressDTO);

        Address addressSalvo = repository.save(address);

        AddressDTO addressDTOSalvo = AddressMapper.toAddressDTO(addressSalvo);

        return addressDTOSalvo;
    }

    public List<AddressDTO> findAll() {
        List<Address> address_list = repository.findAll();

        List<AddressDTO> address_list_dto = address_list.stream()
        .map(AddressMapper::toAddressDTO)
        .collect(Collectors.toList());

        return address_list_dto;
    }
    
    public AddressDTO findById(Long id) {
        Optional<Address> addressOptional = repository.findById(id);

        if(addressOptional.isPresent()) {
            Address address = addressOptional.get();
            return AddressMapper.toAddressDTO(address);
        } 

        throw new EntityNotFoundException("Address com id" + id + " não encontrado!");
    }

    public AddressDTO update(AddressDTO addressDTO) {
        Optional<Address> addressOptional = repository.findById(addressDTO.getId());
    
        if (!addressOptional.isPresent()) {
            throw new EntityNotFoundException("Address com ID " + addressDTO.getId() + " não encontrado");
        }
    
        Address addressSalvo = addressOptional.get();
        Address address = AddressMapper.toAddress(addressDTO);
    
        BeanUtils.copyProperties(address, addressSalvo, "id");
    
        Address addressAtualizado = repository.save(addressSalvo);
    
        return AddressMapper.toAddressDTO(addressAtualizado);
    }

    public void deleteById(Long id) {
        Optional<Address> addressOptional = repository.findById(id);

        if (addressOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Address com id " + id + " não encontrado!");
        }
    }
    
}
