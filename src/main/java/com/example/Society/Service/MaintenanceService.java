package com.example.Society.Service;


import com.example.Society.Config.MaintenanceStatus;
import com.example.Society.DTO.MaintenanceBulkRequestDTO;
import com.example.Society.DTO.MaintenanceRequestDTO;
import com.example.Society.DTO.MaintenanceResponseDTO;
import com.example.Society.Exception.DuplicateMaintenanceBillException;
import com.example.Society.Exception.ResourceNotFoundException;
import com.example.Society.Model.Flat;
import com.example.Society.Model.Maintenance;
import com.example.Society.Repository.FlatRepository;
import com.example.Society.Repository.MaintenanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public MaintenanceResponseDTO createMaintenanceBill(MaintenanceRequestDTO maintenanceRequestDTO) {

        Flat flat = flatRepository.findById(maintenanceRequestDTO.getFlatId())
                .orElseThrow(() -> new ResourceNotFoundException("Flat not found with id: " + maintenanceRequestDTO.getFlatId()));

        boolean billExists = maintenanceRepository.existsByFlatIdAndBillMonthAndBillYear(
                maintenanceRequestDTO.getFlatId(),
                maintenanceRequestDTO.getBillMonth(),
                maintenanceRequestDTO.getBillYear()
        );

        if(billExists){
            throw new DuplicateMaintenanceBillException("Maintenance bill already exists for this flat and billing period");
        }

        BigDecimal ratePerSqft = new BigDecimal("3");


        BigDecimal maintenanceCost  = flat.getFlatSqft().multiply(ratePerSqft);

        Maintenance maintenance = new Maintenance();

        maintenance.setFlat(flat);
        maintenance.setBillMonth(maintenanceRequestDTO.getBillMonth());
        maintenance.setBillYear(maintenanceRequestDTO.getBillYear());
        maintenance.setMaintenanceCost(maintenanceCost);
        maintenance.setLateFee(BigDecimal.ZERO);
        maintenance.setTotalAmount(maintenanceCost);
        maintenance.setDueDate(LocalDate.of(maintenanceRequestDTO.getBillYear(),
                        maintenanceRequestDTO.getBillMonth(),
                        15));
        maintenance.setMaintenanceStatus(MaintenanceStatus.GENERATED);
        maintenance.setGeneratedAt(LocalDateTime.now());
        maintenance.setBillNumber(
                "MB-" + UUID.randomUUID().toString().substring(0,8));

        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        return convertoDTO(savedMaintenance);
    }

    @Transactional
    public List<MaintenanceResponseDTO> generateMaintenanceBillsForAllFlats(MaintenanceBulkRequestDTO maintenanceRequestDTO) {

        List<Flat> flats = flatRepository.findAll();
        List<MaintenanceResponseDTO> generatedBills = new ArrayList<>();

        for (Flat flat : flats) {

            boolean billExists = maintenanceRepository.existsByFlatIdAndBillMonthAndBillYear(
                    flat.getId(),
                    maintenanceRequestDTO.getBillMonth(),
                    maintenanceRequestDTO.getBillYear()
            );

            if(billExists){
                continue;
            }

            BigDecimal ratePerSqft = new BigDecimal("3");
            BigDecimal maintenanceCost  = flat.getFlatSqft().multiply(ratePerSqft);

            Maintenance maintenance = new Maintenance();
            maintenance.setFlat(flat);
            maintenance.setBillMonth(maintenanceRequestDTO.getBillMonth());
            maintenance.setBillYear(maintenanceRequestDTO.getBillYear());
            maintenance.setMaintenanceCost(maintenanceCost);
            maintenance.setLateFee(BigDecimal.ZERO);
            maintenance.setTotalAmount(maintenanceCost);
            maintenance.setDueDate(LocalDate.of(maintenanceRequestDTO.getBillYear(),
                    maintenanceRequestDTO.getBillMonth(),
                    15));
            maintenance.setMaintenanceStatus(MaintenanceStatus.GENERATED);
            maintenance.setGeneratedAt(LocalDateTime.now());
            maintenance.setBillNumber(
                    "MB-" + UUID.randomUUID().toString().substring(0,8)
            );
            Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
            generatedBills.add(convertoDTO(savedMaintenance));
        }

        return generatedBills;
    }

    @Transactional
    public int calculateLateFee() {
        LocalDate today = LocalDate.now(
                java.time.ZoneId.of("Asia/Kolkata")
        );

        List<Maintenance> overdueBills = maintenanceRepository.findByMaintenanceStatusInAndDueDateBefore(
                Arrays.asList(
                        MaintenanceStatus.GENERATED,
                        MaintenanceStatus.PENDING,
                        MaintenanceStatus.OVERDUE
                ),
                today
        );

        int updatedCount = 0;

        BigDecimal penaltyRate = new BigDecimal("0.0175");

        for (Maintenance maintenance : overdueBills) {
            LocalDate dueDate =  maintenance.getDueDate();

            LocalDate firstPenaltyDate = dueDate.plusDays(1);

            if (today.isBefore(firstPenaltyDate)) {
                continue;
            }

            long overdueMonth = ChronoUnit.MONTHS.between(
                    YearMonth.from(firstPenaltyDate),
                    YearMonth.from(today)
            );

            if (today.getDayOfMonth() < 16) {
                overdueMonth--;
            }

            overdueMonth++;

            BigDecimal lateFee = maintenance.getMaintenanceCost().multiply(penaltyRate)
                    .multiply(BigDecimal.valueOf(overdueMonth)).setScale(2, RoundingMode.HALF_UP);

            maintenance.setLateFee(lateFee);
            maintenance.setTotalAmount(maintenance.getMaintenanceCost().add(lateFee));
            maintenance.setMaintenanceStatus(MaintenanceStatus.OVERDUE);

            maintenanceRepository.save(maintenance);
            updatedCount++;
        }

        return updatedCount;
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
