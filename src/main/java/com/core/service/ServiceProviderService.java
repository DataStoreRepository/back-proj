package com.core.service;

import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.core.dto.ServiceProviderDTO;
import com.core.entity.ServiceProvider;
import com.core.entity.User;
import com.core.repository.ServiceProviderRepository;
import com.core.repository.UserRepository;
import com.core.util.ServiceProviderMapper;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository repository;

    @Autowired
    private UserRepository userRepository;

    public ServiceProviderDTO getServiceProviderByUserId(Long userId) {
        // Busca o prestador de serviço pelo userId
        ServiceProvider serviceProvider = repository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Prestador de serviço não encontrado para o usuário!"));
    
        // Converte para DTO e retorna
        return ServiceProviderMapper.toServiceProviderDTO(serviceProvider);
    }

    public ServiceProviderDTO saveServiceProvider(ServiceProviderDTO serviceProviderDTO, MultipartFile imageFile)
            throws IOException {
        // Buscar o User já existente pelo ID fornecido no DTO
        User existingUser = userRepository.findById(serviceProviderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        // Converter a imagem para um array de bytes
        byte[] imageBytes = imageFile.getBytes();

        // Setar a imagem no DTO
        serviceProviderDTO.setImage(imageBytes);

        // Converter o DTO para a entidade ServiceProvider, associando o User
        ServiceProvider serviceProvider = ServiceProviderMapper.toServiceProvider(serviceProviderDTO, existingUser);

        // Salvar o ServiceProvider no banco de dados
        ServiceProvider savedServiceProvider = repository.save(serviceProvider);

        // Retornar o DTO do ServiceProvider salvo
        return ServiceProviderMapper.toServiceProviderDTO(savedServiceProvider);
    }

    public ServiceProviderDTO getServiceProviderById(Long id) {
        // Buscar o ServiceProvider pelo ID
        ServiceProvider serviceProvider = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prestador de serviço não encontrado!"));

        // Converter para DTO e retornar
        return ServiceProviderMapper.toServiceProviderDTO(serviceProvider);
    }

    public List<ServiceProviderDTO> getAllServiceProviders() {
        List<ServiceProvider> serviceProviders = repository.findAll();
        return serviceProviders.stream()
                .map(ServiceProviderMapper::toServiceProviderDTO) // Mapeando cada entidade para DTO
                .collect(Collectors.toList());
    }

}