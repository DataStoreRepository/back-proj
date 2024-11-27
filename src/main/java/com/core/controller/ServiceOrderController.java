package com.core.controller;

import com.core.dto.ServiceOrderDTO;
import com.core.entity.ServiceOrderStatus;
import com.core.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/service-order")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PutMapping("/{id}/rating")
    public ResponseEntity<Void> updateRating(@PathVariable Long id, @RequestBody int rating) {
        serviceOrderService.updateRating(id, rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<ServiceOrderDTO> getServiceOrdersByUserId(@PathVariable Long userId) {
        return serviceOrderService.getServiceOrdersByUserId(userId);
    }

    @GetMapping("/provider/{providerId}")
    public List<ServiceOrderDTO> getServiceOrdersByProviderId(@PathVariable Long providerId) {
        return serviceOrderService.getServiceOrdersByProviderId(providerId);
    }

    @GetMapping
    public List<ServiceOrderDTO> getAllServiceOrders() {
        return serviceOrderService.getAllServiceOrders();
    }

    @GetMapping("/{id}")
    public ServiceOrderDTO getServiceOrderById(@PathVariable Long id) {
        return serviceOrderService.getServiceOrderById(id);
    }

    @PostMapping
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(@RequestBody ServiceOrderDTO serviceOrderDTO) {
        ServiceOrderDTO createdServiceOrder = serviceOrderService.createServiceOrder(serviceOrderDTO);
        return new ResponseEntity<>(createdServiceOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ServiceOrderDTO updateServiceOrder(@PathVariable Long id, @RequestBody ServiceOrderDTO serviceOrderDTO) {
        return serviceOrderService.updateServiceOrder(id, serviceOrderDTO);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateServiceOrderStatus(@PathVariable Long id,
            @RequestBody ServiceOrderStatus newStatus) {
        serviceOrderService.updateServiceOrderStatus(id, newStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrder(@PathVariable Long id) {
        serviceOrderService.deleteServiceOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}