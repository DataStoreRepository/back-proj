// package com.core;

// import com.core.dto.AddressDTO;
// import com.core.dto.UserDTO;
// import com.core.entity.Address;
// import com.core.entity.User;
// import com.core.repository.UserRepository;
// import com.core.service.UserService;
// import com.core.util.UserMapper;

// import jakarta.persistence.EntityNotFoundException;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.Arrays;
// import java.util.NoSuchElementException;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// class UserServiceTest {

// @Mock
// private UserRepository userRepository;

// @InjectMocks
// private UserService userService;

// private AddressDTO validAddress;

// @BeforeEach
// void setUp() {
// MockitoAnnotations.openMocks(this);

// // Configurando um endereço válido para os testes
// validAddress = new AddressDTO(1L, "Rua Teste", "123", "Bairro Teste", "Cidade
// Teste", "Estado Teste");
// }

// @Test
// void shouldSaveUserSuccessfully() {
// // Criando um DTO de usuário válido com endereço
// UserDTO userDTO = new UserDTO(1L, "test@example.com", "password123", "Test
// User", validAddress);
// User user = UserMapper.toUser(userDTO);

// when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
// when(userRepository.save(any(User.class))).thenReturn(user);

// UserDTO savedUser = userService.save(userDTO);

// assertEquals(userDTO.getEmail(), savedUser.getEmail());
// assertEquals(validAddress, savedUser.getAddressDTO());
// verify(userRepository, times(1)).save(any(User.class));
// }

// @Test
// void shouldThrowExceptionIfEmailAlreadyExists() {
// UserDTO userDTO = new UserDTO(1L, "test@example.com", "password123", "Test
// User", validAddress);

// when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

// assertThrows(IllegalArgumentException.class, () ->
// userService.save(userDTO));
// }

// @Test
// void shouldFindAllUsersSuccessfully() {
// // Criando objetos User para simular o repositório
// User user1 = new User(1L, "user1@example.com", "password123", "User One",
// new Address(1L, "Rua Teste", "123", "Bairro Teste", "Cidade Teste", "Estado
// Teste"));
// User user2 = new User(2L, "user2@example.com", "password456", "User Two",
// new Address(2L, "Rua Dois", "456", "Bairro Dois", "Cidade Dois", "Estado
// Dois"));

// // Mockando o repositório
// when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

// // Executando o método de serviço
// var users = userService.findAll();

// // Verificando o número de usuários retornados
// assertEquals(2, users.size());

// // Verificando o mapeamento de dados do primeiro usuário
// assertEquals("user1@example.com", users.get(0).getEmail());
// assertEquals("Rua Teste", users.get(0).getAddressDTO().getStreet());
// assertEquals("User One", users.get(0).getName());

// // Validando que o repositório foi chamado
// verify(userRepository, times(1)).findAll();
// }

// @Test
// void shouldThrowExceptionWhenNoUsersFound() {
// when(userRepository.findAll()).thenReturn(Arrays.asList());

// assertThrows(NoSuchElementException.class, () -> userService.findAll());
// }

// @Test
// void shouldFindUserByIdSuccessfully() {
// // Criando um objeto User para simular o repositório
// User user = new User(1L, "user1@example.com", "password123", "User One",
// new Address(1L, "Rua Teste", "123", "Bairro Teste", "Cidade Teste", "Estado
// Teste"));

// // Mockando o repositório
// when(userRepository.findById(1L)).thenReturn(Optional.of(user));

// // Executando o método de serviço
// var foundUser = userService.findById(1L);

// // Validando os dados retornados
// assertEquals("user1@example.com", foundUser.getEmail());
// assertEquals("User One", foundUser.getName());
// assertEquals("Rua Teste", foundUser.getAddressDTO().getStreet());

// // Verificando que o repositório foi chamado
// verify(userRepository, times(1)).findById(1L);
// }

// @Test
// void shouldThrowExceptionWhenUserNotFoundById() {
// when(userRepository.findById(1L)).thenReturn(Optional.empty());

// assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
// }
// }
