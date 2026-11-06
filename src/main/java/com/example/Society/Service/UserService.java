package com.example.Society.Service;

import com.example.Society.Config.RoleName;
import com.example.Society.DTO.MemberLoginRequestDTO;
import com.example.Society.DTO.UserRequestDTO;
import com.example.Society.DTO.UserResponseDTO;
import com.example.Society.Exception.DuplicateResourceException;
import com.example.Society.Exception.DuplicateUserException;
import com.example.Society.Exception.ResourceNotFoundException;
import com.example.Society.Exception.UserNotFoundException;
import com.example.Society.Model.Member;
import com.example.Society.Model.Role;
import com.example.Society.Model.User;
import com.example.Society.Repository.MemberRepository;
import com.example.Society.Repository.RoleRepository;
import com.example.Society.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private MemberRepository memberRepository;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder, RoleRepository roleRepository, MemberRepository memberRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
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

        Role role = roleRepository.findByName(userRequestDTO.getRole()).
                orElseThrow( () -> new ResourceNotFoundException("Role not found" + userRequestDTO.getRole()));


        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(role);
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

        if (userRepository.existsByUsername(userRequestDTO.getUsername())
                && !user.getUsername().equals(userRequestDTO.getUsername())) {

            throw new DuplicateUserException(
                    "Username already exists: " + userRequestDTO.getUsername()
            );
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())
                && !user.getEmail().equals(userRequestDTO.getEmail())) {

            throw new DuplicateUserException(
                    "Email already exists: " + userRequestDTO.getEmail()
            );
        }

        Role role = roleRepository.findByName(userRequestDTO.getRole()).
                orElseThrow( () -> new ResourceNotFoundException("Role not found" + userRequestDTO.getRole()));

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(role);
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

    // Create login account for an existing member

    @Transactional
    public UserResponseDTO createMemberLogin(
            Long memberId,
            MemberLoginRequestDTO requestDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));

        if (member.getUser() != null) {
            throw new DuplicateResourceException(
                    "This member already has a login account");
        }

        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new DuplicateUserException(
                    "Username already exists: " + requestDTO.getUsername());
        }

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateUserException(
                    "Email already exists: " + requestDTO.getEmail());
        }

        Role role = roleRepository.findByName(RoleName.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MEMBER role not found"));

        User user = new User();

        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        member.setUser(savedUser);
        memberRepository.save(member);

        return convertoUserResponseDTO(savedUser);
    }
}
