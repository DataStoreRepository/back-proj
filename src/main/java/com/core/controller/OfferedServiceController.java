package com.core.controller;

import com.core.dto.OfferedServiceDTO;
import com.core.dto.ServiceSearchDTO;
import com.core.service.OfferedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/offered-service")
public class OfferedServiceController {

    @Autowired
    private OfferedServiceService offeredServiceService;

    @GetMapping
    public List<OfferedServiceDTO> findAll() {
        return offeredServiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferedServiceDTO> findById(@PathVariable Long id) {
        OfferedServiceDTO offeredServiceDTO = offeredServiceService.findById(id);
        return offeredServiceDTO != null
                ? ResponseEntity.ok(offeredServiceDTO)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<OfferedServiceDTO>> searchServices(@RequestBody ServiceSearchDTO searchDTO) {
        List<OfferedServiceDTO> searchResults = offeredServiceService.searchServices(searchDTO);
        return ResponseEntity.ok(searchResults);
    }

    @PostMapping
    public ResponseEntity<OfferedServiceDTO> create(
            @RequestPart("offeredServiceDTO") OfferedServiceDTO offeredServiceDTO,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        // Converter a imagem para um array de bytes
        byte[] imageBytes = imageFile.getBytes();

        // Setar a imagem no DTO
        offeredServiceDTO.setImage(imageBytes);

        OfferedServiceDTO createdOfferedService = offeredServiceService.create(offeredServiceDTO, imageFile);

        return createdOfferedService != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(createdOfferedService)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferedServiceDTO> update(@PathVariable Long id,
            @RequestPart("offeredServiceDTO") OfferedServiceDTO offeredServiceDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            // Obter bytes da imagem (agora dentro do if)
            byte[] imageBytes = imageFile.getBytes();

            // Setar imagem no DTO (agora dentro do if)
            offeredServiceDTO.setImage(imageBytes);

        }

        // Corrigir a chamada do service:
        OfferedServiceDTO updatedOfferedService = offeredServiceService.update(id, offeredServiceDTO, imageFile);

        return updatedOfferedService != null
                ? ResponseEntity.ok(updatedOfferedService)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offeredServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}