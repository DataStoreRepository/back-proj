package com.core.controller;

import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.core.dto.ServiceProviderDTO;
import com.core.service.ServiceProviderService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/service-provider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceProviderDTO> getServiceProviderByUserId(@PathVariable Long userId) {
        ServiceProviderDTO serviceProvider = service.getServiceProviderByUserId(userId);
        return ResponseEntity.ok(serviceProvider);
    }

    

    @PostMapping
    public ResponseEntity<ServiceProviderDTO> saveServiceProvider(
            @RequestPart("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        ServiceProviderDTO savedServiceProvider = service.saveServiceProvider(serviceProviderDTO, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedServiceProvider);
    }

    // Endpoint para buscar um ServiceProvider pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceProviderDTO> getServiceProviderById(@PathVariable Long id) {
        ServiceProviderDTO serviceProvider = service.getServiceProviderById(id);
        return ResponseEntity.ok(serviceProvider);
    }

    @GetMapping
    public ResponseEntity<List<ServiceProviderDTO>> getAllServiceProviders() {
        List<ServiceProviderDTO> serviceProviders = service.getAllServiceProviders();
        return ResponseEntity.ok(serviceProviders);
    }
}