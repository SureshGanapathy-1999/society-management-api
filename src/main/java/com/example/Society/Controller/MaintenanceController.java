package com.example.Society.Controller;

import com.example.Society.DTO.MaintenanceResponseDTO;
import com.example.Society.Service.MaintenanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
