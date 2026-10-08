package com.example.Society.Service;

import com.example.Society.DTO.UserRequestDTO;
import com.example.Society.DTO.UserResponseDTO;
import com.example.Society.Model.User;
import com.example.Society.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // converting to user response

    private UserResponseDTO convertoUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setActive(user.getActive());
        userResponseDTO.setAccountLocked(user.getAccountLocked());
        userResponseDTO.setCredentialsExpired(user.getCredentialsExpired());

        return userResponseDTO;
    }

    // Create user
    public UserResponseDTO createUser( UserRequestDTO userRequestDTO) {
        User user = new User();

        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setEmail(userRequestDTO.getEmail());

        User savedUser = userRepository.save(user);

        return convertoUserResponseDTO(savedUser);
    }

    // Get all user
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll().stream().map(this::convertoUserResponseDTO).toList();

    }

    // Get user by id

    public UserResponseDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        return convertoUserResponseDTO(user);
    }

    // Update user

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return convertoUserResponseDTO(savedUser);
    }

    // Delete user

    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        userRepository.delete(user);
        return "User has been deleted";
    }
}
