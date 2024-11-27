package com.core.controller;

import java.util.List;
import java.util.spi.LocaleNameProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.dto.AddressDTO;
import com.core.service.AddressService;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService AddressService;

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody AddressDTO addressDTO) {
        AddressDTO addressDTOSalvo = AddressService.save(addressDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressDTOSalvo);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> listAll() {

        List<AddressDTO> address_list = AddressService.findAll();

        return ResponseEntity.ok(address_list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Long id) {
        AddressDTO address = AddressService.findById(id);

        return ResponseEntity.ok(address);
    }

    @PutMapping
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = AddressService.update(addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    // Método de deleção (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        AddressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
