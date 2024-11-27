package com.core.util;

import com.core.entity.OfferedService;
import com.core.entity.ServiceOrder;
import com.core.entity.ServiceProvider;
import com.core.entity.User;
import com.core.dto.ServiceOrderDTO;
import com.core.repository.OfferedServiceRepository;
import com.core.repository.ServiceProviderRepository;
import com.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceOrderMapper {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ServiceProviderRepository serviceProviderRepository;

        @Autowired
        private OfferedServiceRepository offeredServiceRepository;

        public ServiceOrderDTO toServiceOrderDTO(ServiceOrder serviceOrder) {
                return new ServiceOrderDTO(
                                serviceOrder.getId(),
                                serviceOrder.getUser().getId(),
                                serviceOrder.getUser().getName(), // Mapeia o nome do usuário
                                serviceOrder.getServiceProvider().getId(),
                                serviceOrder.getOfferedService().getId(),
                                serviceOrder.getOfferedService().getName(), // Mapeia o nome do serviço
                                serviceOrder.getStatus(),
                                serviceOrder.getRating());
        }

        public ServiceOrder toServiceOrder(ServiceOrderDTO serviceOrderDTO) {
                User user = userRepository.findById(serviceOrderDTO.getUserId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                ServiceProvider serviceProvider = serviceProviderRepository
                                .findById(serviceOrderDTO.getServiceProviderId())
                                .orElseThrow(() -> new RuntimeException("Prestador de serviço não encontrado"));
                OfferedService offeredService = offeredServiceRepository.findById(serviceOrderDTO.getOfferedServiceId())
                                .orElseThrow(() -> new RuntimeException("Serviço oferecido não encontrado"));

                ServiceOrder serviceOrder = new ServiceOrder();
                serviceOrder.setUser(user);
                serviceOrder.setServiceProvider(serviceProvider);
                serviceOrder.setOfferedService(offeredService);
                serviceOrder.setStatus(serviceOrderDTO.getStatus());
                serviceOrder.setRating(serviceOrderDTO.getRating());

                return serviceOrder;
        }
}