package com.core.util;

import com.core.dto.ServiceProviderDTO;
import com.core.entity.ServiceProvider;
import com.core.entity.User;

public abstract class ServiceProviderMapper {

    // Converter de ServiceProvider para ServiceProviderDTO
    public static ServiceProviderDTO toServiceProviderDTO(ServiceProvider serviceProvider) {
        return new ServiceProviderDTO(
                serviceProvider.getId(), // ID do ServiceProvider
                serviceProvider.getName(),
                serviceProvider.getDescription(),
                serviceProvider.getExperience(),
                serviceProvider.getImage(),
                serviceProvider.getUser().getId(),
                serviceProvider.getOfferedServices() // Passando apenas o ID do User
        );
    }

    // Converter de ServiceProviderDTO para ServiceProvider, associando um User
    // existente
    public static ServiceProvider toServiceProvider(ServiceProviderDTO serviceProviderDTO, User user) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(serviceProviderDTO.getName());
        serviceProvider.setDescription(serviceProviderDTO.getDescription());
        serviceProvider.setExperience(serviceProviderDTO.getExperience());
        serviceProvider.setImage(serviceProviderDTO.getImage());

        // Associar o User existente
        serviceProvider.setUser(user);

        return serviceProvider;
    }
}
