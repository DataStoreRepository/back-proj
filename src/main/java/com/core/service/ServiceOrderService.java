package com.core.service;

import com.core.entity.ServiceOrder;
import com.core.entity.ServiceOrderStatus;
import com.core.dto.ServiceOrderDTO;
import com.core.repository.ServiceOrderRepository;
import com.core.util.ServiceOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    public void updateRating(Long id, int rating) {
        // Verificar se a ordem de serviço existe
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        // Atualizar a avaliação
        serviceOrder.setRating(rating);

        // Salvar a ordem de serviço atualizada
        serviceOrderRepository.save(serviceOrder);
    }

    public List<ServiceOrderDTO> getServiceOrdersByUserId(Long userId) {
        // Busca as ordens de serviço associadas ao userId
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findByUserId(userId);

        // Mapeia as entidades para DTOs
        return serviceOrders.stream()
                .map(serviceOrderMapper::toServiceOrderDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceOrderDTO> getServiceOrdersByProviderId(Long providerId) {
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findByServiceProviderId(providerId);
        return serviceOrders.stream()
                .map(serviceOrderMapper::toServiceOrderDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceOrderDTO> getAllServiceOrders() {
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
        return serviceOrders.stream()
                .map(serviceOrderMapper::toServiceOrderDTO)
                .collect(Collectors.toList());
    }

    public ServiceOrderDTO getServiceOrderById(Long id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));
        return serviceOrderMapper.toServiceOrderDTO(serviceOrder);
    }

    public ServiceOrderDTO createServiceOrder(ServiceOrderDTO serviceOrderDTO) {
        ServiceOrder serviceOrder = serviceOrderMapper.toServiceOrder(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        return serviceOrderMapper.toServiceOrderDTO(serviceOrder);
    }

    public ServiceOrderDTO updateServiceOrder(Long id, ServiceOrderDTO serviceOrderDTO) {
        ServiceOrder existingOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        existingOrder.setStatus(serviceOrderDTO.getStatus());
        existingOrder.setRating(serviceOrderDTO.getRating());

        serviceOrderRepository.save(existingOrder); // Salva a entidade modificada
        return serviceOrderMapper.toServiceOrderDTO(existingOrder);
    }

    public void updateServiceOrderStatus(Long id, ServiceOrderStatus newStatus) {
        ServiceOrder existingOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        existingOrder.setStatus(newStatus);
        serviceOrderRepository.save(existingOrder);
    }

    public void deleteServiceOrder(Long id) {
        serviceOrderRepository.deleteById(id);
    }

    // ... outros métodos, como aceitar a ordem de serviço, finalizar a ordem de
    // serviço, etc.
}