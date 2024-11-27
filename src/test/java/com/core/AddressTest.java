package com.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.core.dto.AddressDTO;
import com.core.entity.Address;
import com.core.repository.AddressRepository;
import com.core.service.AddressService;
import com.core.util.AddressMapper;
import com.core.util.InputValidation;

public class AddressTest {
    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressService addressService;

    public AddressTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verificarEstado() {

        AddressDTO addressPadrao = new AddressDTO();
        addressPadrao.setState("SP");

        AddressDTO addressCerto = new AddressDTO();
        addressCerto.setState("SC");

        AddressDTO addressErrado = new AddressDTO();
        addressErrado.setState("SCC");

        // testa distintos
        assertNotEquals(addressPadrao, addressErrado, " Os Estados devem ser distindos");

        // teste verifica tamanho estado = 2
        assertEquals(addressCerto.getState().length(), addressPadrao.getState().length(),
                " O tamanho do Estado não pode ter mais de 2 caracteres");

        // teste verifica tamanho estado = 2
        assertTrue(InputValidation.isValidLength(addressCerto.getState(), 2));

        // teste verifica se estado não é null
        assertTrue(InputValidation.isNotEmpty(addressCerto.getState()));
    }

    @Test
    public void testSaveWithNullValues() {
        // Cria um AddressDTO com valores nulos
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setState(null);
        addressDTO.setCity(null);
        addressDTO.setStreet(null);
        addressDTO.setNeighborhood(null);
        addressDTO.setNumber(null);

        // Mapeia o DTO para a entidade Address
        Address address = AddressMapper.toAddress(addressDTO);

        // Simula o comportamento do repositório
        when(repository.save(org.mockito.ArgumentMatchers.any(Address.class))).thenReturn(address);

        // Executa o método de salvar no serviço
        AddressDTO result = addressService.save(addressDTO);

        // Verifica se os dados retornados são nulos, como esperado
        assertEquals(null, result.getState());
        assertEquals(null, result.getCity());
        assertEquals(null, result.getStreet());
        assertEquals(null, result.getNeighborhood());
        assertEquals(null, result.getNumber());
    }

    @Test
    public void testSaveRepositoryException() {
        // Cria um AddressDTO válido
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setState("SP");
        addressDTO.setCity("São Paulo");
        addressDTO.setStreet("Rua Teste");
        addressDTO.setNeighborhood("Centro");
        addressDTO.setNumber("100");

        // Simula o lançamento de uma exceção no repositório
        when(repository.save(org.mockito.ArgumentMatchers.any(Address.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Executa o método de salvar no serviço e captura a exceção
        try {
            addressService.save(addressDTO);
        } catch (RuntimeException e) {
            // Verifica se a exceção foi lançada corretamente
            assertEquals("Database error", e.getMessage());
        }
    }

}
