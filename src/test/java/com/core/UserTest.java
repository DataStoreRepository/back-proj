package com.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.core.dto.AddressDTO;
import com.core.dto.UserDTO;
import com.core.entity.User;
import com.core.repository.UserRepository;
import com.core.service.UserService;
import com.core.util.UserMapper;

public class UserTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    public UserTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveValidUser() {
        // Criando AddressDTO válido
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("São Paulo");
        addressDTO.setState("SP");
        addressDTO.setStreet("Rua Exemplo");
        addressDTO.setNumber("123");
        addressDTO.setNeighborhood("Centro");

        // Criando UserDTO válido
        UserDTO validUser = new UserDTO(null, "Marcus", "marcus.lindo@gmail.com", "password123", addressDTO);

        // Mockando o repositório
        when(repository.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(repository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(UserMapper.toUser(validUser));

        // Executando o serviço
        UserDTO savedUser = userService.save(validUser);

        // Asserções
        assertNotNull(savedUser);
        assertEquals(validUser.getEmail(), savedUser.getEmail(), "O email deve corresponder ao usuário salvo.");
        assertEquals(validUser.getName(), savedUser.getName(), "O nome deve corresponder ao usuário salvo.");
    }

    @Test
    public void testUpdateUser() {
        // Criando usuário existente
        AddressDTO addressDTO = new AddressDTO("São Paulo", "SP", "Rua Velha", "123", "Centro");
        User existingUser = new User(1L, "Marcus", "marcus@email.com", "password123", null);

        // Criando UserDTO com atualização
        UserDTO updateUser = new UserDTO(1L, "Marcus Atualizado", "marcus@email.com", "newpassword123", addressDTO);

        // Mockando o repositório
        when(repository.findById(updateUser.getId())).thenReturn(Optional.of(existingUser));
        when(repository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(UserMapper.toUser(updateUser));

        // Executando o serviço
        UserDTO updatedUser = userService.update(updateUser);

        // Asserções
        assertNotNull(updatedUser);
        assertEquals(updateUser.getName(), updatedUser.getName(), "O nome deve ser atualizado.");
        assertEquals(updateUser.getPassword(), updatedUser.getPassword(), "A senha deve ser atualizada.");
    }

    @Test
    public void testSaveUserWithNullAddress() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("password123");
        userDTO.setAddressDTO(null); // AddressDTO é nulo

        // Espera-se que o método lance uma exceção
        assertThrows(IllegalArgumentException.class, () -> {
            UserMapper.toUser(userDTO);
        }, "Deveria lançar IllegalArgumentException quando AddressDTO é null");
    }
}
