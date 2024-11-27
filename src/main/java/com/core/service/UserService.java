package com.core.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.core.dto.UserDTO;
import com.core.entity.User;
import com.core.repository.UserRepository;
import com.core.util.InputValidation;
import com.core.util.UserMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDTO save(UserDTO userDTO) {

        if (!InputValidation.isNotEmpty(userDTO.getEmail())) {
            throw new IllegalArgumentException("O e-mail não pode estar nulo!");
        }

        if (!InputValidation.isNotEmpty(userDTO.getPassword())) {
            throw new IllegalArgumentException("A senha não pode estar nula!");
        }

        if (!InputValidation.isValidLength(userDTO.getPassword(), 8)) {
            throw new IllegalArgumentException("A senha não pode ter menos que 8 caracteres!");
        }
       
        if (repository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado");
        }

        User user = UserMapper.toUser(userDTO);

        User userSalvo = repository.save(user);

        UserDTO userDTOSalvo = UserMapper.toUserDTO(userSalvo);

        return userDTOSalvo;
    }

    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();

        List<UserDTO> usersDTO = users.stream()
        .map(UserMapper::toUserDTO)
        .collect(Collectors.toList());

        if (usersDTO.isEmpty()) {
            throw new NoSuchElementException("Nenhum usuário encontrado.");
        }

        return usersDTO;
    }

    public UserDTO findById(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserMapper.toUserDTO(user);
        } else {
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado!");
        }
    }

    // quebrado pq da erro de constraint
    public void deleteById(Long id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
    }

    public UserDTO update(UserDTO userDTO) {
        Optional<User> userOptional = repository.findById(userDTO.getId());

        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("Usuário com ID " + userDTO.getId() + " não encontrado");
        }

        User userSalvo = userOptional.get();

        // Mapeando o DTO para a entidade User
        User user = UserMapper.toUser(userDTO);

        // Copiando as propriedades do objeto User atualizado, exceto o "id"
        BeanUtils.copyProperties(user, userSalvo, "id");

        // Salvando o usuário atualizado no banco
        userSalvo = repository.save(userSalvo);

        // Retornando o DTO do usuário atualizado
        return UserMapper.toUserDTO(userSalvo);
    }

    public UserDTO getByEmail(String email) {
        Optional<User> userOptional = repository.findUserByEmail(email);
    
        if (userOptional.isPresent()) {
            return UserMapper.toUserDTO(userOptional.get());
        } else {
            throw new EntityNotFoundException("Usuário com o email " + email + " não encontrado!");
        }
    }
    

}
