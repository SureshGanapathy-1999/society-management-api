package com.example.Society.Service;

import com.example.Society.DTO.UserRequestDTO;
import com.example.Society.DTO.UserResponseDTO;
import com.example.Society.Exception.DuplicateUserException;
import com.example.Society.Exception.UserNotFoundException;
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

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new DuplicateUserException(
                    "Username already exists: " + userRequestDTO.getUsername()
            );
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateUserException(
                    "Email already exists: " + userRequestDTO.getEmail()
            );
        }
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User savedUser = userRepository.save(user);

        return convertoUserResponseDTO(savedUser);
    }

    // Get all user
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll().stream().map(this::convertoUserResponseDTO).toList();

    }

    // Get user by id

    public UserResponseDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));

        return convertoUserResponseDTO(user);
    }

    // Update user

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return convertoUserResponseDTO(savedUser);
    }

    // Delete user

    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
        return "User has been deleted";
    }
}
