package com.example.Society.Controller;

import com.example.Society.DTO.UserRequestDTO;
import com.example.Society.DTO.UserResponseDTO;
import com.example.Society.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create new User

    @PostMapping
    public UserResponseDTO createUser( @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    // Get All User

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUser();
    }

    //Get user by user_id

    @GetMapping("/{user_id}")
    public UserResponseDTO getUser(@PathVariable Long user_id) {
        return userService.getUser(user_id);
    }

    // Update the user

    @PutMapping("/{user_id}")
    public UserResponseDTO updateUser(@PathVariable Long user_id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(user_id, userRequestDTO);
    }

    // Delete the user

    @DeleteMapping("/{user_id}")
    public String deleteUser(@PathVariable Long user_id) {
        return userService.deleteUser(user_id);
    }

}


