package com.core.dto;

import java.util.List;

import com.core.entity.OfferedService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderDTO {
    private Long id;
    private String name;
    private String description;
    private String experience;
    private byte[] image;
    private Long userId;
    private List<OfferedService> offeredServices;
    
}
