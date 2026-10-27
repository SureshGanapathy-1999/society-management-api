package com.example.Society.Controller;

import com.example.Society.DTO.MaintenanceBulkRequestDTO;
import com.example.Society.DTO.MaintenanceRequestDTO;
import com.example.Society.DTO.MaintenanceResponseDTO;
import com.example.Society.Service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public List<MaintenanceResponseDTO> getAllMaintenance(){
        return maintenanceService.getAllMaintenanceBills();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public MaintenanceResponseDTO createMaintenanceBill(@Valid @RequestBody MaintenanceRequestDTO maintenanceRequestDTO){
        return maintenanceService.createMaintenanceBill(maintenanceRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public List<MaintenanceResponseDTO> generateMaintenanceBills(@Valid @RequestBody MaintenanceBulkRequestDTO maintenanceRequestDTO){
        return maintenanceService.generateMaintenanceBillsForAllFlats(maintenanceRequestDTO);
    }

}
