// package com.core;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// import com.core.controller.AuthController;
// import com.core.dto.RegisterDTO;
// import com.core.entity.User;
// import com.core.repository.UserRepository;
// import com.core.service.TokenService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(AuthController.class)
// @Import(TestSecurityConfig.class)
// class AuthTest {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private AuthenticationManager authenticationManager;

// @MockBean
// private UserRepository userRepository;

// @MockBean
// private TokenService tokenService;

// @MockBean
// private PasswordEncoder passwordEncoder; // Mock para evitar problemas no
// contexto

// @BeforeEach
// void setUp() {
// // Mockando o User para simular a autenticação
// User mockUser = new User();
// mockUser.setEmail("test@example.com");
// mockUser.setPassword("password123");

// Authentication authentication = new
// UsernamePasswordAuthenticationToken(mockUser, null);

// when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
// .thenReturn(authentication);

// when(tokenService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");
// }

// @Test
// @WithMockUser(username = "test@example.com", roles = { "USER" })
// public void shouldLoginSuccessfully() throws Exception {
// mockMvc.perform(post("/auth/login")
// .contentType(MediaType.APPLICATION_JSON)
// .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))
// .andExpect(status().isOk())
// .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
// }

// @Test
// void shouldRegisterNewUserSuccessfully() throws Exception {
// RegisterDTO registerDTO = new RegisterDTO("test@example.com", "password123",
// "Test User", null);
// String requestBody = new ObjectMapper().writeValueAsString(registerDTO);

// when(userRepository.existsByEmail(registerDTO.email())).thenReturn(false);

// mockMvc.perform(post("/auth/register")
// .contentType(MediaType.APPLICATION_JSON)
// .content(requestBody))
// .andExpect(status().isOk());
// }

// @Test
// void shouldFailToRegisterUserIfEmailExists() throws Exception {
// RegisterDTO registerDTO = new RegisterDTO("test@example.com", "password123",
// "Test User", null);
// String requestBody = new ObjectMapper().writeValueAsString(registerDTO);

// when(userRepository.existsByEmail(registerDTO.email())).thenReturn(true);

// mockMvc.perform(post("/auth/register")
// .contentType(MediaType.APPLICATION_JSON)
// .content(requestBody))
// .andExpect(status().isBadRequest());
// }
// }
