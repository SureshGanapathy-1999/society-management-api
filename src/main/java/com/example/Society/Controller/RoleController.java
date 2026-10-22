package com.example.Society.Controller;
import com.example.Society.Config.RoleName;
import com.example.Society.DTO.RoleResponseDTO;
import com.example.Society.Service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Get all roles
    @GetMapping
    public List<RoleResponseDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Create role
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RoleResponseDTO createRole(@RequestParam RoleName name) {
        return roleService.createRole(name);
    }
}
