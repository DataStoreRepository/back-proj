package com.core.service;

import com.core.dto.OfferedServiceDTO;
import com.core.dto.ServiceProviderDTO;
import com.core.dto.ServiceSearchDTO;
import com.core.entity.OfferedService;
import com.core.entity.ServiceOrder;
import com.core.entity.ServiceProvider;
import com.core.repository.OfferedServiceRepository;
import com.core.repository.ServiceOrderRepository;
import com.core.repository.ServiceProviderRepository;
import com.core.util.OfferedServiceMapper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferedServiceService {

    @Autowired
    private OfferedServiceRepository offeredServiceRepository;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    public List<OfferedServiceDTO> findAll() {
        return offeredServiceRepository.findAll().stream()
                .map(OfferedServiceMapper::toOfferedServiceDTO)
                .collect(Collectors.toList());
    }

    public OfferedServiceDTO findById(Long id) {
        return offeredServiceRepository.findById(id)
                .map(OfferedServiceMapper::toOfferedServiceDTO)
                .orElse(null);
    }

    public List<OfferedServiceDTO> searchServices(ServiceSearchDTO searchDTO) {
        String serviceName = searchDTO.getServiceName();
        String userLocation = searchDTO.getUserLocation();

        List<ServiceProvider> serviceProviders = new ArrayList<>();

        if (userLocation != null && !userLocation.isEmpty()) {
            // Busca provedores por localização se userLocation for fornecido
            serviceProviders = serviceProviderRepository.findByUserAddressCity(userLocation);
        } else {
            // Se userLocation não for fornecido, busca todos os provedores
            serviceProviders = serviceProviderRepository.findAll();
        }

        // 2. Buscar os serviços oferecidos por esses provedores com o nome especificado
        List<OfferedService> offeredServices = offeredServiceRepository
                .findByServiceProviderInAndNameContainingIgnoreCase(serviceProviders, serviceName);

        // 3. Obter os IDs dos serviços oferecidos
        List<Long> offeredServiceIds = offeredServices.stream()
                .map(OfferedService::getId)
                .collect(Collectors.toList());

        // 4. Buscar todos os pedidos de serviço com esses IDs, ordenados pela nota em
        // ordem decrescente
        List<ServiceOrder> serviceOrders = serviceOrderRepository
                .findByOfferedServiceIdInOrderByRatingDesc(offeredServiceIds);

        // 5. Criar um mapa para armazenar a maior nota de cada serviço
        Map<Long, Integer> serviceRatings = new HashMap<>();
        for (ServiceOrder order : serviceOrders) {
            Long offeredServiceId = order.getOfferedService().getId();
            if (!serviceRatings.containsKey(offeredServiceId)) {
                serviceRatings.put(offeredServiceId, order.getRating());
            }
        }

        // 6. Ordenar os serviços oferecidos com base na maior nota
        offeredServices.sort((s1, s2) -> {
            Integer rating1 = serviceRatings.getOrDefault(s1.getId(), 0);
            Integer rating2 = serviceRatings.getOrDefault(s2.getId(), 0);
            return rating2.compareTo(rating1);
        });

        // 7. Converter para DTO e retornar
        return offeredServices.stream()
                .map(OfferedServiceMapper::toOfferedServiceDTO)
                .collect(Collectors.toList());
    }

    public OfferedServiceDTO create(OfferedServiceDTO offeredServiceDTO, MultipartFile imageFile) throws IOException {
        ServiceProviderDTO serviceProviderDTO = serviceProviderService
                .getServiceProviderById(offeredServiceDTO.getServiceProviderId());

        // Obter bytes da imagem
        byte[] imageBytes = imageFile.getBytes();

        // Setar imagem no DTO
        offeredServiceDTO.setImage(imageBytes);

        if (serviceProviderDTO == null) {
            return null; // Ou lançar uma exceção
        }

        OfferedService offeredService = OfferedServiceMapper.toOfferedService(
                offeredServiceDTO, serviceProviderDTO, serviceProviderRepository);

        return OfferedServiceMapper.toOfferedServiceDTO(offeredServiceRepository.save(offeredService));
    }

    public OfferedServiceDTO update(Long id, OfferedServiceDTO offeredServiceDTO, MultipartFile imageFile)
            throws IOException {
        if (!offeredServiceRepository.existsById(id)) {
            return null; // Ou lançar uma exceção
        }

        OfferedService existingService = offeredServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com ID: " + id));

        ServiceProviderDTO serviceProviderDTO = serviceProviderService
                .getServiceProviderById(offeredServiceDTO.getServiceProviderId());

        // Verifica se uma nova imagem foi enviada
        if (imageFile != null && !imageFile.isEmpty()) {
            // Obter bytes da imagem
            byte[] imageBytes = imageFile.getBytes();

            // Setar imagem no DTO
            offeredServiceDTO.setImage(imageBytes);

            // Atualiza a imagem no serviço existente
            existingService.setImage(imageBytes);
        }

        if (serviceProviderDTO == null) {
            return null; // Ou lançar uma exceção
        }

        // Atualiza os demais campos do serviço existente
        existingService.setName(offeredServiceDTO.getName());
        existingService.setDescription(offeredServiceDTO.getDescription());
        existingService.setPrice(offeredServiceDTO.getPrice());

        offeredServiceDTO.setId(id);

        // Salva o serviço existente com as alterações (incluindo a imagem, se houver)
        OfferedService updatedService = offeredServiceRepository.save(existingService);

        return OfferedServiceMapper.toOfferedServiceDTO(updatedService);
    }

    public void delete(Long id) {
        offeredServiceRepository.deleteById(id);
    }
}