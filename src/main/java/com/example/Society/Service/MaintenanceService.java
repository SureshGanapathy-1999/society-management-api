package com.example.Society.Service;


import com.example.Society.DTO.MaintenanceRequestDTO;
import com.example.Society.DTO.MaintenanceResponseDTO;
import com.example.Society.Model.Flat;
import com.example.Society.Model.Maintenance;
import com.example.Society.Repository.FlatRepository;
import com.example.Society.Repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final FlatRepository flatRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,  FlatRepository flatRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.flatRepository = flatRepository;
    }

    public List<MaintenanceResponseDTO> getAllMaintenanceBills() {

        List<Maintenance> maintenanceList =  maintenanceRepository.findAll();

        return maintenanceList.stream()
                .map(this::convertoDTO)
                .toList();
    }

    public MaintenanceResponseDTO createMaintenanceBill(MaintenanceRequestDTO maintenanceRequestDTO) {

        Flat flat = flatRepository.findById(maintenanceRequestDTO.getFlatId())
                .orElseThrow(() -> new RuntimeException("flat not found"));

        boolean billExists = maintenanceRepository.existsByFlatIdAndBillNumberAndBillYear(
                maintenanceRequestDTO.getFlatId(),
                maintenanceRequestDTO.getBillMonth(),
                maintenanceRequestDTO.getBillYear()
        );

        if(billExists){
            throw new RuntimeException("maintenance bill already exists for this flat");
        }

        BigDecimal ratePerSqft = new BigDecimal("3");


        BigDecimal maintenanceCost  = flat.getFlatSqft().multiply(ratePerSqft);

        Maintenance maintenance = new Maintenance();

        maintenance.setFlat(flat);

    }

    private MaintenanceResponseDTO convertoDTO(Maintenance maintenance) {

        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();

        dto.setId(maintenance.getId());
        dto.setBillNumber(maintenance.getBillNumber());
        dto.setFlatId(maintenance.getFlat().getId());
        dto.setBillMonth(maintenance.getBillMonth());
        dto.setBillYear(maintenance.getBillYear());
        dto.setMaintenanceCost(maintenance.getMaintenanceCost());
        dto.setLateFee(maintenance.getLateFee());
        dto.setTotalAmount(maintenance.getTotalAmount());
        dto.setDueDate(maintenance.getDueDate());
        dto.setMaintenanceStatus(maintenance.getMaintenanceStatus());
        dto.setGeneratedAt(maintenance.getGeneratedAt());

        return dto;
    }
}
