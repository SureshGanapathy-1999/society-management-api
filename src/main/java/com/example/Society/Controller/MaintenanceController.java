package com.example.Society.Controller;

import com.example.Society.DTO.MaintenanceRequestDTO;
import com.example.Society.DTO.MaintenanceResponseDTO;
import com.example.Society.Service.MaintenanceService;
import jakarta.validation.Valid;
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

    @PostMapping
    public MaintenanceResponseDTO createMaintenanceBill(@Valid @RequestBody MaintenanceRequestDTO maintenanceRequestDTO){
        return maintenanceService.createMaintenanceBill(maintenanceRequestDTO);
    }
}
