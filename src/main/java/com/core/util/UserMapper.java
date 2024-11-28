package com.core.util;

import com.core.dto.AddressDTO;
import com.core.dto.UserDTO;
import com.core.entity.UserMarket;

public abstract class UserMapper {
    
    public static UserDTO toUserDTO(UserMarket user) {

        AddressDTO addressDTO = null;

        if(user.getAddress() != null) {
            addressDTO = AddressMapper.toAddressDTO(user.getAddress());
        }
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            addressDTO
        );
    }

    public static UserMarket toUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo!");
        }

        return new UserMarket(
            userDTO.getId(),
            userDTO.getName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            AddressMapper.toAddress(userDTO.getAddressDTO())
        );
    }
}
