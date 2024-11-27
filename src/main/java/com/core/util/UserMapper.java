package com.core.util;

import com.core.dto.AddressDTO;
import com.core.dto.UserDTO;
import com.core.entity.User;

public abstract class UserMapper {
    
    public static UserDTO toUserDTO(User user) {

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

    public static User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo!");
        }

        return new User(
            userDTO.getId(),
            userDTO.getName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            AddressMapper.toAddress(userDTO.getAddressDTO())
        );
    }
}
