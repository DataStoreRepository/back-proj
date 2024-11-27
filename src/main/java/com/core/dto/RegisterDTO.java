package com.core.dto;

import com.core.entity.Address;

public record RegisterDTO (String email, String password, String name, Address address) {
    
}
