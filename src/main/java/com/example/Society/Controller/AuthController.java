package com.example.Society.Controller;

import com.example.Society.DTO.LoginRequestDTO;
import com.example.Society.DTO.LoginResponseDTO;
import com.example.Society.Model.User;
import com.example.Society.Repository.UserRepository;
import com.example.Society.Service.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,  JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponseDTO(
                "Login Successful!",
                user.getUsername(),
                user.getRole().getName().name(),
                token
        );
    }
}
