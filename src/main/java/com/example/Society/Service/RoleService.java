
package com.example.Society.Service;

import com.example.Society.Config.RoleName;
import com.example.Society.DTO.RoleResponseDTO;
import com.example.Society.Exception.DuplicateResourceException;
import com.example.Society.Model.Role;
import com.example.Society.Repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Get all roles
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Create role
    public RoleResponseDTO createRole(RoleName name) {

        if (roleRepository.existsByName(name)) {
            throw new DuplicateResourceException(
                    "Role already exists: " + name);
        }

        Role role = new Role();
        role.setName(name);

        Role savedRole = roleRepository.save(role);

        return convertToDTO(savedRole);
    }

    // Convert Entity to DTO
    private RoleResponseDTO convertToDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
